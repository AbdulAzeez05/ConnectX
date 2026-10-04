const cloudName = process.env.REACT_APP_CLOUDINARY_CLOUD_NAME;
const uploadPreset = process.env.REACT_APP_CLOUDINARY_PRESET;

export const uploadToCloudinary = async (pics, fileType) => {
  if (pics && fileType) {
    console.log("pics", pics, fileType);

    const data = new FormData();
    data.append("file", pics);
    data.append("upload_preset", uploadPreset);
    data.append("cloud_name", cloudName);

    const res = await fetch(
      `https://api.cloudinary.com/v1_1/${cloudName}/${fileType}/upload`,
      {
        method: "post",
        body: data,
      }
    );

    const fileData = await res.json();
    console.log("url : ", fileData.secure_url);
    return fileData.secure_url;
  } else {
    console.log("error");
  }
};