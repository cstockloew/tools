package uploadopensourceplugin.handlers;

import java.util.Properties;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;

public class SendEmailHandler extends AbstractHandler {
	
	
	
	public SendEmailHandler(){
		
	}

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		Properties props = new Properties();
	    props.put("mail.smtp.host", "my-mail-server");
	    props.put("mail.from", "me@example.com");
	    Session session = Session.getInstance(props, null);
	    
	    MimeMessage msg = new MimeMessage(session);
	    
	    MimeBodyPart part1 = new MimeBodyPart();
	    MimeBodyPart part2 = new MimeBodyPart();
	    try {
			part1.setText("hallo");
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
		return null;
	}

}
