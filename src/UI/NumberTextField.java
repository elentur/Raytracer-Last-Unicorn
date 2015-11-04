package UI;

/**
 * Created by Marcus Baetz on 03.11.2015.
 *
 * @author Marcus Bätz
 */
import javafx.scene.control.TextField;

public class NumberTextField extends TextField {
    public NumberTextField(String text) {
        super(text);
        this.focusedProperty().addListener(a->{

            if(!this.isFocused()) {
                String pattern1 = "[-+]?[0-9]+\\.[0-9]+";
                String pattern2 = "[-+]?[0-9]+";
                if (!this.getText().matches(pattern1)) {
                    if(this.getText().matches(pattern2)){
                        this.setText(this.getText() + ".0");
                    }else {
                        this.setText("0.0");
                    }
                }
            }
        });
    }

    @Override public void replaceText(int start, int end, String text) {
        if (text.matches("[0-9]") || text.matches(".") || text.matches("-") || text == "") {
            super.replaceText(start, end, text);
        }
    }

    @Override public void replaceSelection(String text) {
        if (text.matches("[0-9]") || text.matches(".") || text.matches("-") || text == "") {
            super.replaceSelection(text);
        }
    }


}
