package jp.keio.jfn.wat.bundle;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import java.util.Locale;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

/**
 * This class manages the locale. Users have the choice between English and Japanese.
 *
 * @author Alex Kabbach
 */
@Controller
@Scope("session")
@ManagedBean
public class LocaleBean {

	private Locale locale;
	public final static String ENGLISH = "en";
	public final static String JAPANESE = "jp";

	@PostConstruct
	private void init(){
		//this.locale = FacesContext.getCurrentInstance().getViewRoot().getLocale();
		this.locale = new Locale(ENGLISH);
	}

	public void change(){
		if(locale.getLanguage().equals(ENGLISH)){
			this.locale = new Locale(JAPANESE);
		}else{
			this.locale = new Locale(ENGLISH);
		}
	}

	public Locale getLocale() {
		return locale;
	}

	public String getLanguage() {
		return locale.getLanguage();
	}

	public void setLanguage(String language) {
		this.locale = new Locale(language);
	}

}
