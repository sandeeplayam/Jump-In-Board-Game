//import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class SaveFile {
//	private boolean progress;
	private String[][] boardSave1;
	private String[][] boardSave2;
	private String[][] boardSave3;
	
	public SaveFile() {};
	
//	public SaveFile(String[][] boardSave, int saveSlot) {
//		
//	}
	
//	public SaveFile(String[][] boardSave1, String[][] boardSave2, String[][] boardSave3) {
//		this.boardSave1 = boardSave1;
//		this.boardSave2 = boardSave2;
//		this.boardSave3 = boardSave3;
//	}
	
//	@XmlElement
//	public boolean isProgress() {
//		return progress;
//	}

	@XmlElement
	public String[][] getBoardSave1() {
		return boardSave1;
	}

	@XmlElement
	public String[][] getBoardSave2() {
		return boardSave2;
	}

	@XmlElement
	public String[][] getBoardSave3() {
		return boardSave3;
	}
	
//	public void setProgress(boolean progress) {
//		this.progress = progress;
//	}

	public void setBoardSave1(String[][] board) {
		this.boardSave1 = board;
	}
	
	public void setBoardSave2(String[][] boardSave2) {
		this.boardSave2 = boardSave2;
	}

	public void setBoardSave3(String[][] boardSave3) {
		this.boardSave3 = boardSave3;
	}
	
	

}
