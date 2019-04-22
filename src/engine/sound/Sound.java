package engine.sound;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import engine.io.FileUtils;

public class Sound {
	private URL soundUrl;
	private ArrayList<Clip> clips;
	
	public Sound(String path) {
		clips = new ArrayList<Clip>();
		
		try {
			soundUrl = FileUtils.getFileURL(path);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		
		loadClip(); // Preload
	}
	
	private Clip findFreeClip() {
		for (int i = 0; i < clips.size(); i++) {
			Clip clip = clips.get(i);
			if ( !clip.isRunning() )
				return clip;
		}
		
		return null;
	}
	
	private Clip loadClip() {
		Clip free = null;
		try {
			AudioInputStream is = AudioSystem.getAudioInputStream(soundUrl);
			free = AudioSystem.getClip();
			free.open(is);
			is.close();
		} catch (UnsupportedAudioFileException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (LineUnavailableException e) {
			e.printStackTrace();
		}
		
		if ( free != null )
			clips.add(free);
		
		return free;
	}
	
	public void play() {
		if ( soundUrl == null )
			throw new RuntimeException("Sound not loaded.");
		
		Clip free = findFreeClip();
		if ( free == null )
			free = loadClip();
		
		free.setFramePosition(0);
		free.start();
	}
}
