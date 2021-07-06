package com.example.botfacebook;

import java.io.IOException;
import java.net.URISyntaxException;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.ClientProtocolException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;

import com.example.gsonobj.ResponseObjCurrent;
import com.example.utils.UtilsInfor;

import me.ramswaroop.jbot.core.common.Controller;
import me.ramswaroop.jbot.core.common.EventType;
import me.ramswaroop.jbot.core.common.JBot;
import me.ramswaroop.jbot.core.facebook.Bot;
import me.ramswaroop.jbot.core.facebook.FbService;
import me.ramswaroop.jbot.core.facebook.models.Attachment;
import me.ramswaroop.jbot.core.facebook.models.Button;
import me.ramswaroop.jbot.core.facebook.models.Element;
import me.ramswaroop.jbot.core.facebook.models.Event;
import me.ramswaroop.jbot.core.facebook.models.Message;
import me.ramswaroop.jbot.core.facebook.models.Payload;
import me.ramswaroop.jbot.core.facebook.models.User;

@JBot
//@Profile("facebook")
public class FbBot extends Bot {

	/**
	 * Set this property in {@code application.properties}.
	 */
	@Value("${fbBotToken}")
	private String fbToken;

	/**
	 * Set this property in {@code application.properties}.
	 */
	@Value("${fbPageAccessToken}")
	private String pageAccessToken;

	@Override
	public String getFbToken() {
		return fbToken;
	}

	@Override
	public String getPageAccessToken() {
		return pageAccessToken;
	}

	/**
	 * Sets the "Get Started" button with a payload "hi". It also set the "Greeting
	 * Text" which the user sees when it opens up the chat window. Uncomment the
	 * {@code @PostConstruct} annotation only after you have verified your webhook.
	 */
//	@PostConstruct
	public void init() {
		setGetStartedButton("hi");
		setGreetingText(new Payload[] { new Payload().setLocale("default")
				.setText("Hello bạn, đây là demo bot chat đập troai của anh Đức cũng đập troai nốt,"
						+ " các bạn có thể tra cứu rất nhiều thông tin hữu ích tại đây. "
						+ "Hãy click vào \"Get Started\" hoặc chat \"Hi\" để bắt đầu sử dụng bot chat.") });
	}

	@Controller(events = { EventType.MESSAGE })
	public void otherChat(Event e) {
		reply(e, "Bot đang được phát triển, còn nhỏ dại, mong bạn thông cảm!");
	}

	/**
	 * This method gets invoked when a user clicks on the "Get Started" button or
	 * just when someone simply types hi, hello or hey. When it is the former, the
	 * event type is {@code EventType.POSTBACK} with the payload "hi" and when
	 * latter, the event type is {@code EventType.MESSAGE}.
	 *
	 * @param event
	 */
	@Controller(events = { EventType.MESSAGE, EventType.POSTBACK }, pattern = "^(?i)(hi|hello|hey|chào bot|chào|hola)$")
	public void onGetStarted(Event event) {
		// quick reply buttons
		Button[] quickReplies = new Button[] { new Button().setContentType("text").setTitle("Có").setPayload("yes"),
				new Button().setContentType("text").setTitle("Không").setPayload("no") };
		reply(event,
				new Message().setText(
						"Hello, mình là bot đẹp troai, bạn có muốn tra cứu thông tin hay trò chuyện mới mềnh không?")
						.setQuickReplies(quickReplies));
	}

	/**
	 * This method gets invoked when the user clicks on a quick reply button whose
	 * payload is either "yes" or "no".
	 *
	 * @param event
	 */
	@Controller(events = EventType.QUICK_REPLY, pattern = "(yes|no)")
	public void onReceiveQuickReply(Event event) {
		if ("yes".equals(event.getMessage().getQuickReply().getPayload())) {
			reply(event, "OK, sau đây là 1 số gợi ý để tra cứu thông tin: \n"
					+ "- Gõ 'Thời tiết Hà Nội' hoặc '-cw Hà Nội' để xem thời tiết hiện tại ở Hà Nội\n"
					+ "- Gõ 'Thời khóa biểu hôm nay' để xem thời khóa biểu ngày hôm nay\n"
					+ "- Gõ 'Show các ví dụ demo' để xem 1 số ví dụ demo của cậu chủ\n"
					+ "- Hoặc gõ -help để xem tất cả các lệnh được hỗ trợ!");
		} else {
			reply(event, "Bai bai, gặp lại bạn sau nhé");
		}
	}
	@Controller(events = EventType.QUICK_REPLY, pattern = "(dk|!dk)")
	public void onReceiveXacNhanDangKy(Event event) {
		if ("!dk".equals(event.getMessage().getQuickReply().getPayload())) {
			reply(event, "OK, sau đây là 1 số gợi ý để tra cứu thông tin: \n"
					+ "- Gõ 'Thời tiết Hà Nội' hoặc '-cw Hà Nội' để xem thời tiết hiện tại ở Hà Nội\n"
					+ "- Gõ 'Thời khóa biểu hôm nay' để xem thời khóa biểu ngày hôm nay\n"
					+ "- Gõ 'Show các ví dụ demo' để xem 1 số ví dụ demo của cậu chủ\n"
					+ "- Hoặc gõ -help để xem tất cả các lệnh được hỗ trợ!");
		} else {
			reply(event, "Bai bai, gặp lại bạn sau nhé");
		}
	}
	@Controller(events = EventType.MESSAGE, pattern = "(?i:Thời tiết |-cw |thời tiết)")
	public void thoiTiet(Event e) {
		String msgSender = e.getMessage().getText();
		if("Thời tiết".toLowerCase().equals(StringUtils.left(msgSender.toLowerCase(),9))) {
			String location = msgSender.substring(10,msgSender.length());
			if(location.trim().length() == 0) {
				reply(e,"Có vẻ bạn chưa nhập tên địa điểm cần tra cứu thông tin thời tiết.");
				return;
			}
			try {
				ResponseObjCurrent objCurrent = UtilsInfor.getCurrentWheather(location);
				if(objCurrent.equals(null)) {
					reply(e, "Không tìm thấy địa điểm bạn muốn tra cứu! \n Mẹo: hãy nhập tên địa chỉ không dấu hoặc theo chuẩn tiếng Anh sẽ có kết quả chính xác hơn.");
				}else {
					int temp = (int)(objCurrent.getMain().getTemp()-273.15);
					int feel_like = (int)(objCurrent.getMain().getFeels_like()-273.15);
					int pressure = objCurrent.getMain().getPressure();
					int humidity = objCurrent.getMain().getHumidity();
					Double speed_wind = objCurrent.getWind().getSpeed();
					int visibility = objCurrent.getVisibility()/1000;
					String content = "Thời tiết hiện tại ở: "+location+"\n"
							+ "Nhiêt độ: "+temp+" °C\n"
							+ "Cảm giác như: "+feel_like+" °C\n"
							+ "Độ ẩm: "+humidity+" %\n"
							+ "Áp suất: "+pressure+" Hpa\n"
							+ "Tốc độ gió: "+speed_wind+" m/s\n"
							+ "Tầm nhìn xa: "+visibility+" km";
					reply(e, content);
				}
			}catch (Exception ex) {
				reply(e, "Không tìm thấy địa điểm bạn muốn tra cứu! \n Mẹo: hãy nhập tên địa chỉ không dấu hoặc theo chuẩn tiếng Anh sẽ có kết quả chính xác hơn.");
			}
		}else if("-cw".equals(StringUtils.left(msgSender, 3))) {
			String location = msgSender.substring(4,msgSender.length());
			if(location.trim().length() == 0) {
				reply(e,"Có vẻ bạn chưa nhập tên địa điểm cần tra cứu thông tin thời tiết.");
				return;
			}
			try {
				ResponseObjCurrent objCurrent = UtilsInfor.getCurrentWheather(location);
				if(objCurrent.equals(null)) {
					reply(e, "Không tìm thấy địa điểm bạn muốn tra cứu! \n Mẹo: hãy nhập tên địa chỉ không dấu hoặc theo chuẩn tiếng Anh sẽ có kết quả chính xác hơn.");
				}else {
					int temp = (int)(objCurrent.getMain().getTemp()-272.15);
					int feel_like = (int)(objCurrent.getMain().getFeels_like()-272.15);
					int pressure = objCurrent.getMain().getPressure();
					int humidity = objCurrent.getMain().getHumidity();
					Double speed_wind = objCurrent.getWind().getSpeed();
					int visibility = objCurrent.getVisibility()/1000;
					String content = "Thời tiết hiện tại ở: "+location+"\n"
							+ "Nhiêt độ: "+temp+" °C\n"
							+ "Cảm giác như: "+feel_like+" °C\n"
							+ "Độ ẩm: "+humidity+" %\n"
							+ "Áp suất: "+pressure+" Hpa\n"
							+ "Tốc độ gió: "+speed_wind+" m/s\n"
							+ "Tầm nhìn xa: "+visibility+" km";
					reply(e, content);
				}
			}catch (Exception ex) {
				reply(e, "Không tìm thấy địa điểm bạn muốn tra cứu! \n Mẹo: hãy nhập tên địa chỉ không dấu hoặc theo chuẩn tiếng Anh sẽ có kết quả chính xác hơn.");
			}
		}else {
			reply(e, "Có vẻ bạn đã nhập sai lệnh, kiểm tra lại bạn nhé!");
		}
	}
	@Controller(events = EventType.MESSAGE, pattern = "(?i:ví dụ demo|vi du demo|vi du|demo)")
	public void showButtonsWebDemo(Event event) {
		Button[] buttons = new Button[] {
				new Button().setType("web_url").setUrl("https://leanhduc-forecast.herokuapp.com")
						.setTitle("API thời tiết"),
				new Button().setType("web_url").setUrl("https://leanhduc-forecast.herokuapp.com/crawler").setTitle("Crawler dữ liệu thời tiết") };
		reply(event, new Message().setAttachment(new Attachment().setType("template").setPayload(
				new Payload().setTemplateType("button").setText("Có 2 ví dụ demo cho bạn tham khảo.").setButtons(buttons))));
	}
	@Controller(events = EventType.MESSAGE, pattern = "-help")
	public void showHelp(Event event) {
		reply(event, "- Gõ '-cw <Tên địa điểm>' hoặc 'Thời tiết + <Tên địa điểm>' để tra cứu thời tiết hiện tại của 1 địa điểm\n"
				+"- Gõ '-tkb' hoặc 'Thời khóa biểu hôm nay' để xem hôm nay học những môn gì và ở phòng nào\n"
				+"- Gõ '-dk' để xác nhận đăng ký nhận thông tin thời tiết mỗi 3 tiếng\n"
				+ "- Gõ '-hdk' để xác nhận hủy đăng ký nhận thông tin thời tiết\n"
				+"- ======Sẽ cập nhật thêm chức năng sau======");
	}
	@Controller(events = EventType.MESSAGE, pattern = "(-dk|-hdk)")
	public void ConfirmDk(Event event) {
		reply(event, "Chức năng đang được cậu chủ của bot phát triển!");
	}
	@Controller(events = EventType.MESSAGE, pattern = "(?i:thời khóa biểu hôm nay|Thời khóa biểu hôm nay|-tkb)")
	public void showTKB(Event event) {
		reply(event, "Chức năng đang được phát triển. Bot vô cùng xin lỗi vì sự bất tiện này!");
	}
	
	@Controller(events = EventType.MESSAGE, pattern = "(?i:Hà óc chó|Ha oc cho|Vương óc chó|Vuong oc cho|Tín óc chó|Tin oc cho|Hiệp óc chó|Hiep oc cho|Hiep ngu|Hiệp ngu)")
	public void trashTalks(Event event) throws URISyntaxException {
		//lấy thông tin từ user id của người gửi
		com.example.gsonobj.User user = UtilsInfor.getUserInfor(event.getSender().getId(), pageAccessToken);
		String msgback = "Bạn "+user.getFirst_name() + " " + user.getLast_name() +" đã nói đúng lại còn nói to!";
		reply(event, msgback);
	}
	
	@Controller(events = EventType.MESSAGE, pattern = "(?i)(user-info|info)")
	public void getId(Event event) throws URISyntaxException {
		com.example.gsonobj.User user = UtilsInfor.getUserInfor(event.getSender().getId(), pageAccessToken);
		String msg = "info người gửi: "+user.toString()+"\n người gửi: "+event.getSender().getId()+"\n raw: "+ event.getSender().toString();
		reply(event, msg);
	}

	
	@Controller(events = EventType.MESSAGE, pattern = "(?i)(bye|tata|ttyl|cya|see you|gặp lại sau|pp)")
	public void showGithubLink(Event event) {
		reply(event,
				new Message().setAttachment(new Attachment().setType("template")
						.setPayload(new Payload().setTemplateType("button").setText("Gặp lại bạn sau!")
								.setButtons(new Button[] { new Button().setType("web_url").setTitle("Chi tiết thời tiết tại đây!")
										.setUrl("https://leanhduc-forecast.herokuapp.com/") }))));
	}

	@Controller(events = EventType.MESSAGE, pattern = "(?i)(bot óc chó|bot ngu)")
	public void trashTalk(Event event) {
		reply(event, "Bot là người nhân hậu, không chửi thề! Cút ngay không bố mày thông nát ass mày bây giờ!");
	}

	// Conversation feature of JBot

	/**
	 * Type "setup meeting" to start a conversation with the bot. Provide the name
	 * of the next method to be invoked in {@code next}. This method is the starting
	 * point of the conversation (as it calls
	 * {@link Bot#startConversation(Event, String)} within it. You can chain methods
	 * which will be invoked one after the other leading to a conversation.
	 *
	 * @param event
	 */
	@Controller(pattern = "(?i)(setup meeting)", next = "confirmTiming")
	public void setupMeeting(Event event) {
		startConversation(event, "confirmTiming"); // start conversation
		reply(event, "Cool! At what time (ex. 15:30) do you want me to set up the meeting?");
	}

	/**
	 * This method will be invoked after {@link FbBot#setupMeeting(Event)}. You need
	 * to call {@link Bot#nextConversation(Event)} to jump to the next question in
	 * the conversation.
	 *
	 * @param event
	 */
	@Controller(next = "askTimeForMeeting")
	public void confirmTiming(Event event) {
		reply(event,
				"Your meeting is set at " + event.getMessage().getText() + ". Would you like to repeat it tomorrow?");
		nextConversation(event); // jump to next question in conversation
	}

	/**
	 * This method will be invoked after {@link FbBot#confirmTiming(Event)}. You can
	 * call {@link Bot#stopConversation(Event)} to end the conversation.
	 *
	 * @param event
	 */
	@Controller(next = "askWhetherToRepeat")
	public void askTimeForMeeting(Event event) {
		if (event.getMessage().getText().contains("yes")) {
			reply(event, "Okay. Would you like me to set a reminder for you?");
			nextConversation(event); // jump to next question in conversation
		} else {
			reply(event, "No problem. You can always schedule one with 'setup meeting' command.");
			stopConversation(event); // stop conversation only if user says no
		}
	}

	/**
	 * This method will be invoked after {@link FbBot#askTimeForMeeting(Event)}. You
	 * can call {@link Bot#stopConversation(Event)} to end the conversation.
	 *
	 * @param event
	 */
	@Controller
	public void askWhetherToRepeat(Event event) {
		if (event.getMessage().getText().contains("yes")) {
			reply(event, "Great! I will remind you tomorrow before the meeting.");
		} else {
			reply(event, "Okay, don't forget to attend the meeting tomorrow :)");
		}
		stopConversation(event); // stop conversation
	}
}
