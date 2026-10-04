package com.ConnectX.UserRepository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ConnectX.models.Chat;
import com.ConnectX.models.User;

public interface ChatRepository extends JpaRepository<Chat,Integer>{
	
	public List<Chat> findByUsersId(Integer userId);

	
	@Query("select c from Chat c where :user member of c.users And :reqUser member of c.users")
	public Chat findChatByUsersId(@Param("user") User user ,@Param("reqUser") User reqUser);
}
