import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * A simple POJO to be converted into an XML file
 * 
 * @author David Ou
 *
 */
@XmlRootElement
public class SaveFile {
	private String[][] boardSave1;
	private String[][] boardSave2;
	private String[][] boardSave3;

	public SaveFile() {
	};

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
