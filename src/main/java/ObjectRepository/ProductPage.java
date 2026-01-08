package ObjectRepository;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import genericUtility.WebDriverUtility;

public class ProductPage {

	WebDriver driver;
	WebDriverUtility wUtil=new WebDriverUtility();
	public ProductPage(WebDriver driver) {
		
		this.driver=driver;
		PageFactory.initElements(driver,this);
	}
	
	@FindBy(xpath="//span[text()='Add Product']")
	private WebElement addProductBtn;
	
	@FindBy(name="productName")
	private WebElement prodName;
	
	@FindBy(name="productCategory")
	private WebElement prodCategory;
	
	@FindBy(name="quantity")
	private WebElement quantity;
		
	
	@FindBy(name="price")
	private WebElement price;
	
	@FindBy(name="vendorId")
	private WebElement vendor;
	
	@FindBy(xpath="//button[contains(text(),'Add')]")
	private WebElement addProdSubmitBtn;
	
	@FindBy(xpath="//select[@class='form-control']")
	private WebElement selectbydropdown;
	
	@FindBy(xpath="//input[@placeholder='Search by product Name']")
	private WebElement searchbytextfield;
	
	@FindBy(xpath="//i[@title='Delete']")
	private List<WebElement> deleteBtns;
	
	public List<WebElement> getDeleteBtns() {
		return deleteBtns;
	}

	public WebElement getConfirmdelete() {
		return confirmdelete;
	}

	@FindBy(xpath="//input[@value='Delete']")
	private WebElement confirmdelete;
	
	public WebDriver getDriver() {
		return driver;
	}

	public WebElement getAddProductBtn() {
		return addProductBtn;
	}

	public WebElement getProdName() {
		return prodName;
	}

	public WebElement getProdCategory() {
		return prodCategory;
	}

	public WebElement getQuantity() {
		return quantity;
	}

	public WebElement getPrice() {
		return price;
	}

	public WebElement getVendor() {
		return vendor;
	}

	public WebElement getAdd() {
		return addProdSubmitBtn;
	}
	
	public WebElement getselectbydropdown() {
		return selectbydropdown;
	}
	public WebElement getsearchbytextfield() {
		return searchbytextfield;
	}
	
	public void createProduct(String fullproductname, String stockQuantity, String unitPrice,int categoryvalue,int vendor_ID) {
		getProdName().sendKeys(fullproductname);
		getQuantity().clear();
		getQuantity().sendKeys(stockQuantity);
		getPrice().clear();
		getPrice().sendKeys(unitPrice);
		wUtil.select(getProdCategory(),categoryvalue );
		wUtil.select(getVendor(), vendor_ID);
		wUtil.waitForVisibilityofElement(driver, getAdd());
		getAdd().click();
		
		
	}
public void deleteProduct(String value,String name_value) throws InterruptedException{
		
	wUtil.waitForPagetoLoad(driver);
	HomePage homepage=new HomePage(driver);
	wUtil.waitForVisibilityofElement(driver, homepage.getProduct());
	homepage.getProduct().click();
	wUtil.waitForVisibilityofElement(driver, selectbydropdown);
	wUtil.select(value, selectbydropdown);			
	wUtil.waitForVisibilityofElement(driver, searchbytextfield);
	Thread.sleep(500);
	getsearchbytextfield().sendKeys(name_value);
		
	}
	
	
}
