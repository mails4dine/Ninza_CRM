package ObjectRepository;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import genericUtility.WebDriverUtility;

public class LoginPage {
	 WebDriverUtility wutil=new WebDriverUtility();
	WebDriver driver;
	public LoginPage(WebDriver driver) {
		
		this.driver=driver;
		PageFactory.initElements(driver,this);
	}
	@FindBy(id="username")
	private WebElement UN;
	
	@FindBy(id="inputPassword")
	private WebElement PWD;
	
	@FindBy(xpath="//button[contains(text(),'Sign In')]")
	private WebElement LoginBtn;
	
	

	public WebElement getUN() {
		return UN;
	}



	public WebElement getPWD() {
		return PWD;
	}

	
	public WebElement getLoginBtn() {
		return LoginBtn;
	}
	
	public void loginToApplication(String username, String password) throws InterruptedException {
		
		getUN().sendKeys(username);
        getPWD().sendKeys(password); 
        wutil.waitForVisibilityofElement(driver,getLoginBtn());
        getLoginBtn().click();
       
	}
	
}
