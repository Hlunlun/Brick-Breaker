package application;

//reference
//https://www.tpisoftware.com/tpu/articleDetails/1432

public enum Music {
	
	ballup("src/audio/ballup.wav"),
	brickDestroy("src/audio/brickDestroy.mp3"),
	NoCopyRightSounds("src/audio/NoCopyRightSounds.mp3"),
	tick("src/audio/tick.mp3");

	
	private String path;
	
	Music(String path) {
		this.path = path;
	}
	
	public String getPath() {
        return path;
    }

}
