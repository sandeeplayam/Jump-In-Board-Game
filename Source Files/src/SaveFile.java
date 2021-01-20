//import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class SaveFile {
	private String[][] board;
	
	public SaveFile() {};
	
	public SaveFile(String[][] board) {
		this.board = board;
	}

	@XmlElement
	public String[][] getBoard() {
		return board;
	}

	public void setBoard(String[][] board) {
		this.board = board;
	}
	

}
