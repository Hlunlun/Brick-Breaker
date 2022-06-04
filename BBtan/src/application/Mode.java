package application;

//tell different mode in BBtan
//remember to set mode name in every modeController's method--initialize!
public enum Mode {
	
	Homepage("Homepage.fxml"),
	Menu("Menu.fxml"),
	Simple("Simple.fxml"),
	Endless("Endless.fxml"),
	CountDown("CountDown.fxml"),
	Times("Times.fxml"),
	Pause("Pause.fxml"),
	Win("Win.fxml"),
	Gameover("Gameover.fxml");
	
	public static Mode mode;
	
	private String path;
	Mode(String path) {
		this.path=path;
	}

	public String getPath() {
		return path;
	}
	
}
