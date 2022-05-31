package application;

//reference
//https://www.tpisoftware.com/tpu/articleDetails/1432

public enum Music {
	
	ballup("src/audio/ballup.wav"),
	brickDestroy("src/audio/brickDestroy.mp3"),
	tick("src/audio/tick.mp3"),
	click("src/audio/click.mp3"),
	startgame("src/audio/startgame.mp3"),
	background("src/audio/BGM.mp3"),
	boom("src/audio/boom.mp3");

	
	private String path;
	
	Music(String path) {
		this.path = path;
	}
	
	public String getPath() {
        return path;
    }

}
