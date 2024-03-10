
public class MenuFunction {
	
	private int menuNum;
	private String menuOptionText;
	
	public MenuFunction(int menuNum, String menuOptionText) {
		this.menuNum = menuNum;
		this.menuOptionText = menuOptionText;
	}
	
	public int getMenuNum() {
		return menuNum;
	}

	@Override
	public String toString() {
		return "[" + menuNum + "] " + menuOptionText;
	}

}
