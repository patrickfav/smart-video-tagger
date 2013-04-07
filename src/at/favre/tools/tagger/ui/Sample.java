package at.favre.tools.tagger.ui;

import at.favre.tools.tagger.analyzer.config.ConfigManager;
import at.favre.tools.tagger.analyzer.config.EContainedTypes;
import at.favre.tools.tagger.analyzer.config.ScannerConfig;
import at.favre.tools.tagger.analyzer.io.VideoFileVisitor;
import at.favre.tools.tagger.analyzer.util.FileCounter;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.DirectoryChooser;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.util.ResourceBundle;
public class Sample implements Initializable {

	@FXML
	private TextField dirTextField;

	@FXML
	private ProgressBar progressBar;

	@FXML
	private Label progressLabel;

	@FXML
	private Button start;

	private File choosenFile;

	@FXML
	private void handleButtonAction(ActionEvent event) {
		if(choosenFile != null) {
			ScannerConfig config = new ScannerConfig(EContainedTypes.MIXED,true,true, "C:\\Program Files (x86)\\ffmpeg\\ffmpeg-win32-static\\bin\\ffmpeg.exe");

			final VideoFileVisitor visitor = new VideoFileVisitor();

			Task<Void> task = new Task<Void>() {
				@Override
				public Void call() {
					try {
						visitor.setListener(new VideoFileVisitor.FileScanProgressListener() {
							@Override
							public void onProgressUpdate(int filesScanned) {
								updateProgress(filesScanned,Double.valueOf(progressLabel.getText()));
							}
						});
						Files.walkFileTree(choosenFile.toPath(), visitor);

					} catch (IOException e) {
						e.printStackTrace();
					}

					return null;
				}
			};
			task.progressProperty().addListener(new ChangeListener<Number>() {
				@Override
				public void changed(ObservableValue<? extends Number> observableValue, Number number, Number number2) {
					progressBar.setProgress(number2.doubleValue());
				}
			});

			task.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
				@Override
				public void handle(WorkerStateEvent workerStateEvent) {
					progressBar.setVisible(false);
					start.setDisable(false);
					dirTextField.setDisable(false);
				}
			});

			progressBar.setVisible(true);
			start.setDisable(true);
			dirTextField.setDisable(true);
			new Thread(task).start();
			///progressBar.setProgress(progress.getValue());




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


		Task<Void> task = new Task<Void>() {
			@Override
			public Void call() {
				int count = FileCounter.countAllFilesWithGivenExtensions(choosenFile, ConfigManager.getInstance().getExtensions());
				updateMessage(String.valueOf(count));

				return null;
			}
		};


		task.messageProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observableValue, String oldValue, String newValue) {
				progressLabel.setText(newValue);
			}
		});

		task.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
			@Override
			public void handle(WorkerStateEvent workerStateEvent) {
				progressBar.setVisible(false);
				dirTextField.setDisable(false);
			}
		});

		progressBar.setVisible(true);
		dirTextField.setDisable(true);
		new Thread(task).start();
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
