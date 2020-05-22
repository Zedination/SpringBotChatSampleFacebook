package com.example.botfacebook;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;

import me.ramswaroop.jbot.core.common.Controller;
import me.ramswaroop.jbot.core.common.EventType;
import me.ramswaroop.jbot.core.common.JBot;
import me.ramswaroop.jbot.core.facebook.Bot;
import me.ramswaroop.jbot.core.facebook.models.Attachment;
import me.ramswaroop.jbot.core.facebook.models.Button;
import me.ramswaroop.jbot.core.facebook.models.Element;
import me.ramswaroop.jbot.core.facebook.models.Event;
import me.ramswaroop.jbot.core.facebook.models.Message;
import me.ramswaroop.jbot.core.facebook.models.Payload;

@JBot
@Profile("facebook")
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
	@PostConstruct
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
			reply(event, "OK, bạn có thể gõ các lệnh sau để tra cứu thông tin: \n"
					+ "- Dự báo thời tiết Hà Nội\n"
					+ "- Xem các môn học hôm nay\n"
					+ "- Show các ví dụ demo");
		} else {
			reply(event, "Bai bai, gặp lại bạn sau nhé");
		}
	}

	/**
	 * This method is invoked when the user types "Show Buttons" or something which
	 * has "button" in it as defined in the {@code pattern}.
	 *
	 * @param event
	 */
	@Controller(events = EventType.MESSAGE, pattern = "(?i:ví dụ demo)")
	public void showButtons(Event event) {
		Button[] buttons = new Button[] {
				new Button().setType("web_url").setUrl("https://leanhduc-forecast.herokuapp.com")
						.setTitle("API thời tiết"),
				new Button().setType("web_url").setUrl("https://leanhduc-forecast.herokuapp.com/crawler").setTitle("Crawler dữ liệu thời tiết") };
		reply(event, new Message().setAttachment(new Attachment().setType("template").setPayload(
				new Payload().setTemplateType("button").setText("Có 2 ví dụ demo cho bạn tham khảo.").setButtons(buttons))));
	}

	/**
	 * This method is invoked when the user types "Show List" or something which has
	 * "list" in it as defined in the {@code pattern}.
	 *
	 * @param event
	 */
	@Controller(events = EventType.MESSAGE, pattern = "(?i:list)")
	public void showList(Event event) {
		Element[] elements = new Element[] {
				new Element().setTitle("AnimateScroll").setSubtitle("A jQuery Plugin for Animating Scroll.")
						.setImageUrl("https://plugins.compzets.com/images/as-logo.png")
						.setDefaultAction(new Button().setType("web_url").setMessengerExtensions(true)
								.setUrl("https://plugins.compzets.com/animatescroll/")),
				new Element().setTitle("Windows on Top").setSubtitle("Keeps a specific Window on Top of all others.")
						.setImageUrl("https://plugins.compzets.com/images/compzets-logo.png")
						.setDefaultAction(new Button().setType("web_url").setMessengerExtensions(true)
								.setUrl("https://www.compzets.com/view-upload.php?id=702&action=view")),
				new Element().setTitle("SimpleFill").setSubtitle("Simplest form filler ever.")
						.setImageUrl("https://plugins.compzets.com/simplefill/chrome-extension/icon-64.png")
						.setDefaultAction(new Button().setType("web_url").setMessengerExtensions(true)
								.setUrl("https://plugins.compzets.com/simplefill/")) };
		reply(event, new Message().setAttachment(new Attachment().setType("template")
				.setPayload(new Payload().setTemplateType("list").setElements(elements))));
	}

	/**
	 * Show the github project url when the user says bye.
	 *
	 * @param event
	 */
	@Controller(events = EventType.MESSAGE, pattern = "(?i)(bye|tata|ttyl|cya|see you|gặp lại sau|pp)")
	public void showGithubLink(Event event) {
		reply(event,
				new Message().setAttachment(new Attachment().setType("template")
						.setPayload(new Payload().setTemplateType("button").setText("Bye. Happy coding!")
								.setButtons(new Button[] { new Button().setType("web_url").setTitle("View Docs")
										.setUrl("https://blog.rampatra.com/how-to-make-facebook-bots-in-java") }))));
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
