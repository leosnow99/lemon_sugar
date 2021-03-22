package com.sugar.chat.dao;

import com.sugar.chat.model.ChatMsg;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author bytedance
 */

public interface ChatMsgRepository extends JpaRepository<ChatMsg, String>, JpaSpecificationExecutor<String> {
    /**
     * 批量更新接收到的消息
     * @param msgIds 接收的消息id 列表
     */
    @Modifying
    @Query(value = "update ChatMsg cm set cm.receive = true where cm.id in(?1)")
    void updateMsgReceived(List<String> msgIds);
}
