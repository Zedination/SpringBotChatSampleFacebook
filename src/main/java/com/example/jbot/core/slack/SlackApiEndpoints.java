package com.example.jbot.core.slack;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class SlackApiEndpoints {

    /**
     * Endpoint for Slack Api
     */
    @Value("${slackApi}")
    private String slackApi;

    /**
     * @return endpoint for RTM.connect()
     */
    public String getRtmConnectApi() {
        return slackApi + "/rtm.connect?token={token}";
    }

    public String getImListApi() {
        return slackApi + "/conversations.list?token={token}&limit={limit}&cursor={cursor}&types=im";
    }
}
