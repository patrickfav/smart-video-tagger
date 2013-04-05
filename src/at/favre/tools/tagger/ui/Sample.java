package at.favre.tools.tagger.ui;

import at.favre.tools.tagger.analyzer.config.EContainedTypes;
import at.favre.tools.tagger.analyzer.config.ScannerConfig;
import at.favre.tools.tagger.analyzer.io.VideoFileVisitor;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.DirectoryChooser;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ResourceBundle;
public class Sample implements Initializable {

	@FXML
	private TextField dirTextField;

	@FXML
	private ProgressBar progressBar;

	private File choosenFile;

	@FXML
	private void handleButtonAction(ActionEvent event) {
		if(choosenFile != null) {
			final Path path = FileSystems.getDefault().getPath("H:\\== SERIES ==");

			ScannerConfig config = new ScannerConfig(EContainedTypes.MIXED,true,true, "C:\\Program Files (x86)\\ffmpeg\\ffmpeg-win32-static\\bin\\ffmpeg.exe");

			final VideoFileVisitor visitor = new VideoFileVisitor(new VideoFileVisitor.FileScanProgressListener() {
				@Override
				public void onProgressUpdate(int filesScanned) {
					progressBar.setProgress(filesScanned/1000);
				}
			});

			new Task<Void>() {
				@Override
				public Void call() {
					try {
						Files.walkFileTree(path, visitor);
						updateProgress(1,2);
					} catch (IOException e) {
						e.printStackTrace();
					}

					return null;
				}
			}.call();


			//FileAnalyzer analyzer = new FileAnalyzer(visitor.getRoot(),config);
			//analyzer.analyzeAll();
			//analyzer.parseToLog();

			//WorkerManager.getInstance().getThreadPool().shutdown();
		}
	}

	private void onTextFieldClick(MouseEvent event) {

		DirectoryChooser chooser = new DirectoryChooser();
		chooser.setTitle("Choose Video Folder");
		chooser.setInitialDirectory(choosenFile != null ? choosenFile : new File(System.getProperty("user.home")));
		File tempFolder = chooser.showDialog(dirTextField.getScene().getWindow());

		if(tempFolder != null) {
			choosenFile =tempFolder;
			dirTextField.setText(choosenFile.getAbsolutePath());
		}


	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		dirTextField.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent mouseEvent) {
				onTextFieldClick(mouseEvent);
			}
		});
	}
}
