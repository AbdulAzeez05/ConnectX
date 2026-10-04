import {
  Avatar,
  Backdrop,
  CircularProgress,
  Divider,
  Grid,
  IconButton,
} from "@mui/material";
import React, { Fragment, useEffect, useRef, useState } from "react";
import AddIcCallIcon from "@mui/icons-material/AddIcCall";
import VideocamIcon from "@mui/icons-material/Videocam";
import WestIcon from "@mui/icons-material/West";
import ChatMessage from "../../components/Message/ChatMessage";
import AddPhotoAlternateIcon from "@mui/icons-material/AddPhotoAlternate";
import { useDispatch, useSelector } from "react-redux";
import {
  createChat,
  createMessage,
  getAllChats,
} from "../../Redux/Message/message.action";
import UserChatCard from "../../components/Message/UserChatCard";
import ChatBubbleOutlineIcon from "@mui/icons-material/ChatBubbleOutline";
import { uploadToCloudinary } from "../../utis/uploadToCloudniry";
import "./Message.css";
import { searchUser } from "../../Redux/Auth/auth.action";
import SearchUser from "../../components/SearchUser/SearchUser";
import { API_BASE_URL } from "../../config/api";
import SockJS from "sockjs-client";
import Stomp from "stompjs";

const Message = () => {
  const dispatch = useDispatch();
  const { chat, auth } = useSelector((store) => store);
  const [currentChat, setCurrentChat] = useState(null);
  const [messages, setMessages] = useState([]);
  const [loading, setLoading] = useState(false);
  const [selectedImage, setSelectedImage] = useState(null);
  const [inputMessage, setInputMessage] = useState("");
  const chatContainerRef = useRef(null);

  // WebSocket State & Reference
  const stompRef = useRef(null);
  const [connected, setConnected] = useState(false);

  // Fetch all chats on component mount or chat state change
  useEffect(() => {
    dispatch(getAllChats());
  }, []);

  // Update messages state when a new message comes in through Redux/REST
  useEffect(() => {
    if (chat.message) {
      setMessages((prev) => [...prev, chat.message]);
    }
  }, [chat.message]);

  // Establish WebSocket connection ONCE on mount
  useEffect(() => {
    let cancelled = false;
    const sock = new SockJS(`${API_BASE_URL}/ws`);
    const stomp = Stomp.over(sock);
    stomp.debug = null; // Mute console debug logs

    stomp.connect(
      {},
      () => {
        if (cancelled) {
          stomp.disconnect();
          return;
        }
        stompRef.current = stomp;
        setConnected(true);
      },
      (err) => console.log("WebSocket error:", err)
    );

    return () => {
      cancelled = true;
      setConnected(false);
      stompRef.current = null;
      if (stomp.connected) stomp.disconnect();
    };
  }, []);

  // Subscribe to active chat topic whenever connection or active chat changes
  useEffect(() => {
    if (!connected || !currentChat) return;

    const subscription = stompRef.current.subscribe(
      `/topic/chat/${currentChat.id}`,
      (payload) => {
        const receivedMessage = JSON.parse(payload.body);
        // Ignore broadcast if sent by current user (already appended via REST POST)
        if (receivedMessage.user?.id === auth.reqUser?.id) return;
        setMessages((prev) => [...prev, receivedMessage]);
      }
    );

    return () => {
      subscription.unsubscribe();
    };
  }, [connected, currentChat?.id, auth.reqUser?.id]);

  // Send message over WebSocket
  const sendMessageToServer = (message) => {
    if (stompRef.current?.connected && currentChat) {
      stompRef.current.send(
        `/app/chat/${currentChat.id}`,
        {},
        JSON.stringify(message)
      );
    }
  };

  const handleCreateMessage = (value) => {
    const message = {
      chatId: currentChat.id,
      content: value,
      image: selectedImage,
    };
    const data = { message, sendToServer: sendMessageToServer };
    dispatch(createMessage(data));
    setSelectedImage(null);
  };

  const handleSelectImage = async (event) => {
    setLoading(true);
    const imgUrl = await uploadToCloudinary(event.target.files[0], "image");
    setSelectedImage(imgUrl);
    setLoading(false);
  };

  const handleCreateChat = (userId) => {
    dispatch(createChat({ userId }));
  };

  // Scroll to bottom whenever messages list updates
  useEffect(() => {
    if (chatContainerRef.current) {
      chatContainerRef.current.scrollTop =
        chatContainerRef.current.scrollHeight;
    }
  }, [messages]);

  return (
    <Fragment>
      <Grid className="h-screen overflow-y-hidden" container>
        <Grid className="px-5 bg-[#191c29]" xs={3} item>
          <div className="flex h-full justify-between space-x-2">
            <div className="w-full">
              <div className="flex space-x-4 items-center py-5">
                <WestIcon />
                <h1 className="text-xl font-bold">Home</h1>
              </div>

              <div className="h-[83vh]">
                <div>
                  <SearchUser handleClick={handleCreateChat} />
                </div>
                <div className="h-full space-y-4 mt-5 overflow-y-scroll hideScrollbar">
                  {chat.chats.map((item) => (
                    <div
                      key={item.id}
                      onClick={() => {
                        setCurrentChat(item);
                        setMessages(item.messages || []);
                      }}
                      className="cursor-pointer bg-[#212534] rounded-md"
                    >
                      <UserChatCard item={item} />
                    </div>
                  ))}
                </div>
              </div>
            </div>
          </div>
        </Grid>

        <Grid className="h-full" item xs={9}>
          {currentChat ? (
            <div>
              <div className="flex justify-between items-center bg-[#191c29] border-l p-5">
                <div className="flex items-center space-x-3">
                  <Avatar src="https://cdn.pixabay.com/photo/2016/04/17/20/19/woman-1335487_640.jpg" />
                  <p>{currentChat.chat_name}</p>
                </div>
                <div className="flex space-x-3">
                  <IconButton>
                    <AddIcCallIcon />
                  </IconButton>
                  <IconButton>
                    <VideocamIcon />
                  </IconButton>
                </div>
              </div>
              <div
                ref={chatContainerRef}
                className="hideScrollbar overflow-y-scroll h-[82vh] px-2 space-y-5 py-5 pb-10"
              >
                {messages.map((item, i) => (
                  <ChatMessage key={item.id || i} item={item} />
                ))}
              </div>

              <div className="sticky bottom-0 border-l">
                {selectedImage && (
                  <img
                    className="w-[5rem] h-[5rem] object-cover px-2"
                    src={selectedImage}
                    alt=""
                  />
                )}
                <div className="bg-[#191c29] py-5 flex items-center justify-center space-x-5">
                  <input
                    onKeyPress={(e) => {
                      if (
                        e.key === "Enter" &&
                        (e.target.value || selectedImage)
                      ) {
                        handleCreateMessage(e.target.value);
                        setInputMessage("");
                      }
                    }}
                    className="bg-transparent border border-[#3b4054] rounded-full w-[90%] py-3 px-5"
                    type="text"
                    placeholder="Type message..."
                    value={inputMessage}
                    onChange={(e) => setInputMessage(e.target.value)}
                  />
                  <div>
                    <input
                      type="file"
                      accept="image/*"
                      onChange={handleSelectImage}
                      style={{ display: "none" }}
                      id="image-input"
                    />
                    <label htmlFor="image-input">
                      <AddPhotoAlternateIcon />
                    </label>
                  </div>
                </div>
              </div>
            </div>
          ) : (
            <div className="h-full space-y-5 flex flex-col justify-center items-center">
              <ChatBubbleOutlineIcon sx={{ fontSize: "15rem" }} />
              <p className="text-xl font-semibold">No Chat Selected</p>
            </div>
          )}
        </Grid>
      </Grid>
      <Backdrop
        sx={{ color: "#fff", zIndex: (theme) => theme.zIndex.drawer + 1 }}
        open={loading}
      >
        <CircularProgress color="inherit" />
      </Backdrop>
    </Fragment>
  );
};

export default Message;