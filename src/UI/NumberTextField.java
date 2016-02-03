package UI;

import javafx.beans.property.*;
import javafx.event.EventHandler;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

/**
 * Textfield implementation that accepts formatted number and stores them in a
 * BigDecimal property The user input is formatted when the focus is lost or the
 * user hits RETURN.
 *
 * @author Thomas Bolz
 */
public class NumberTextField extends TextField {

    private final NumberFormat nf;
    private ObjectProperty<BigDecimal> number = new SimpleObjectProperty<>();
    public DoubleProperty doubleProperty = new SimpleDoubleProperty();
    public IntegerProperty integerProperty = new SimpleIntegerProperty();


    public final BigDecimal getNumber() {
        return number.get();
    }
    public final double getDouble() {

        return number.get().doubleValue();
    }
    public final int getInteger() {
        return number.get().intValue();
    }

    public final void setNumber(BigDecimal value) {
        number.set(value);
    }
    public final void setNumber(int i) {
        number.set(new BigDecimal(i));
    }
    public final void setNumber(double d) {
        number.set(new BigDecimal(d));
    }
    public final void setNumber(String  s) {
        number.set(new BigDecimal(s));
    }

    public ObjectProperty<BigDecimal> numberProperty() {
        return number;
    }

    public NumberTextField() {
        this(BigDecimal.ZERO);
    }
    public NumberTextField(int i) {
        this(new BigDecimal(i),NumberFormat.getIntegerInstance(Locale.US));
    }
    public NumberTextField(double d) {
        this(new BigDecimal(d),NumberFormat.getInstance(Locale.US));
    }
    public NumberTextField(BigDecimal value) {
        this(value, NumberFormat.getInstance());
        //initHandlers();
    }

    public NumberTextField(BigDecimal value, NumberFormat nf) {
        super();
        this.nf = nf;
        this.nf.setMaximumFractionDigits(8);
        initHandlers();
        setNumber(value);

    }

    private void initHandlers() {
        doubleProperty.addListener(a->{
            setNumber(doubleProperty.getValue());
        });
        integerProperty.addListener(a->{
            setNumber(integerProperty.getValue());
        });
        // try to parse when focus is lost or RETURN is hit
        addEventHandler(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(final KeyEvent event) {
            if(event.getCode().equals(KeyCode.ENTER)) {
                parseAndFormatInput();
            }
                //}


            }
        });
        focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.booleanValue()) {
                parseAndFormatInput();
            }
        });

        // Set text in field if BigDecimal property is changed from outside.
        numberProperty().addListener((obserable, oldValue, newValue) -> {
            setText(nf.format(newValue));
        });

    }

    /**
     * Tries to parse the user input to a number according to the provided
     * NumberFormat
     */

    private void parseAndFormatInput() {
        try {
            String input = getText();
            if (input == null || input.length() == 0) {
                return;
            }
            Number parsedNumber = nf.parse(input);
            BigDecimal newValue = new BigDecimal(parsedNumber.toString());
            setNumber(newValue);
            integerProperty.setValue(newValue);
            doubleProperty.setValue(newValue);
            selectAll();
          //  fireEvent(new ActionEvent());
        } catch (ParseException ex) {
            // If parsing fails keep old number
            setText(nf.format(number.get()));
        }

    }
}