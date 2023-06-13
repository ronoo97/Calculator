package calculator.gui;

import static calculator.core.Core.BUTTONS;
import calculator.gui.events.ButtonEvent;
import calculator.gui.events.KeyboardEvent;
import calculator.gui.events.DragDropEvent;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public final class GUI extends JFrame implements IFrequentColors {

    private static GUI instance = null;

    private static final int PANEL_WIDTH = 580;
    private static final int PANEL_HEIGHT = 305;

    private static final int TITLE_HEIGHT = 35;
    private static final int TEXTFIELD_HEIGHT = 70;

    private static final int DEF_B_SIZE = 18;
    private static final int BUTTONS_SIZE[][] = {
        {DEF_B_SIZE, DEF_B_SIZE, DEF_B_SIZE, 25, DEF_B_SIZE, DEF_B_SIZE, DEF_B_SIZE, DEF_B_SIZE, 16, 16},
        {DEF_B_SIZE, DEF_B_SIZE, DEF_B_SIZE, 25, DEF_B_SIZE, DEF_B_SIZE, DEF_B_SIZE, DEF_B_SIZE, 25, 16},
        {DEF_B_SIZE, DEF_B_SIZE, DEF_B_SIZE, 30, DEF_B_SIZE, DEF_B_SIZE, DEF_B_SIZE, DEF_B_SIZE, 25, 16},
        {DEF_B_SIZE, DEF_B_SIZE, DEF_B_SIZE, 25, 25, DEF_B_SIZE, DEF_B_SIZE, 20, 16, DEF_B_SIZE}
    };

    private static final int TEXT_PADDING_RIGHT = 15;
    private static final int TEXT_PADDING_LEFT = 15;

    private JLabel operations;
    private JLabel values;

    private static final boolean TEST_MODE = false;

    private GUI() {

        Container Panel = getContentPane();

        //properties
        setSize(PANEL_WIDTH, PANEL_HEIGHT);

        setResizable(TEST_MODE);
        BorderLayout mainLayout = new BorderLayout();
        Panel.setLayout(mainLayout);
        Panel.setFocusable(true);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation((screenSize.width / 2) - (PANEL_WIDTH / 2), (screenSize.height / 2) - (PANEL_HEIGHT / 2));

        //style
        Panel.setBackground(MAIN_COLOR);
        setUndecorated(!TEST_MODE);

        ImageIcon icon = new ImageIcon(getClass().getResource("/calculator/gui/img/icon.png"));
        setIconImage(icon.getImage());

        //elements
        Component PanelTitle = GenerateTitle(Panel);
        Component PanelContainer = GeneratePanel(Panel);

        //layout
        mainLayout.addLayoutComponent(PanelTitle, BorderLayout.NORTH);
        mainLayout.addLayoutComponent(PanelContainer, BorderLayout.SOUTH);

        //event
        DragDropEvent MoveListener = new DragDropEvent(this);
        PanelTitle.addMouseListener(MoveListener);
        PanelTitle.addMouseMotionListener(MoveListener);

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });

        Panel.addKeyListener(new KeyboardEvent());

        setVisible(true);
    }

    private Component GenerateTitle(Container c) {

        //container
        CustomJPanel title = new CustomJPanel(
                new Dimension(PANEL_WIDTH, TITLE_HEIGHT),
                SECONDARY_COLOR,
                new GridBagLayout()
        );
        c.add(title);

        //titletext
        JLabel titleText = new JLabel("Számológép");
        titleText.setSize(getMaximumSize());

        titleText.setForeground(MAIN_TEXT_COLOR);
        titleText.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        12
                )
        );

        CustomJPanel titleTextContainer = new CustomJPanel(
                new Dimension((PANEL_WIDTH - 90), TITLE_HEIGHT),
                MAIN_COLOR,
                new FlowLayout(FlowLayout.LEFT, 10, 8)
        );
        titleTextContainer.add(titleText);
        title.add(titleTextContainer);

        //buttons
        CustomJPanel buttonContainer = new CustomJPanel(
                new Dimension(90, TITLE_HEIGHT),
                MAIN_COLOR,
                new GridLayout()
        );
        GenerateTitleButtons(buttonContainer);

        title.add(buttonContainer);
        return title;
    }

    private void GenerateTitleButtons(CustomJPanel container) {

        //minimize
        CustomButton minimizeButton = new CustomButton(
                new ImageIcon(getClass().getResource("/calculator/gui/img/minimize.png")),
                new Dimension(45, TITLE_HEIGHT),
                TITLE_BUTTON_TEXT_COLOR,
                TITLE_BUTTON_COLOR,
                TITLE_BUTTON_TEXT_HOVER_COLOR,
                TITLE_BUTTON_HOVER_COLOR
        );
        minimizeButton.addMouseListener(new ButtonEvent(ButtonEvent.Tasks.MINIMIZE, this));
        container.add(minimizeButton);

        //close
        CustomButton closeButton = new CustomButton(
                new ImageIcon(getClass().getResource("/calculator/gui/img/close.png")),
                new Dimension(45, TITLE_HEIGHT),
                TITLE_BUTTON_TEXT_COLOR,
                TITLE_BUTTON_COLOR,
                TITLE_BUTTON_TEXT_HOVER_COLOR,
                TITLE_EXIT_BUTTON_HOVER_COLOR
        );
        closeButton.addMouseListener(new ButtonEvent(ButtonEvent.Tasks.EXIT));
        container.add(closeButton);

    }

    private Component GeneratePanel(Container c) {

        BorderLayout panelContainerLayout = new BorderLayout();
        CustomJPanel panelContainer = new CustomJPanel(
                new Dimension(PANEL_WIDTH, 0),
                Color.red,
                panelContainerLayout
        );

        Component textField = GenerateTextField(panelContainer);
        Component buttonField = GenerateButtonField(panelContainer);

        c.add(panelContainer);

        panelContainerLayout.addLayoutComponent(textField, BorderLayout.NORTH);
        panelContainerLayout.addLayoutComponent(buttonField, BorderLayout.SOUTH);

        return panelContainer;
    }

    private Component GenerateTextField(Container c) {

        BorderLayout textFieldLayout = new BorderLayout();
        CustomJPanel container = new CustomJPanel(
                new Dimension(PANEL_WIDTH, TEXTFIELD_HEIGHT),
                MAIN_COLOR,
                textFieldLayout
        );

        CustomJPanel smallTextContainer = new CustomJPanel(
                new Dimension(PANEL_WIDTH, TEXTFIELD_HEIGHT / 2),
                MAIN_COLOR,
                new BorderLayout()
        );
        CustomJPanel Textcontainer = new CustomJPanel(
                new Dimension(PANEL_WIDTH, TEXTFIELD_HEIGHT / 2),
                MAIN_COLOR,
                new BorderLayout()
        );

        JLabel smallText = new JLabel();
        smallText.setHorizontalAlignment(SwingConstants.RIGHT);
        smallText.setVerticalAlignment(SwingConstants.BOTTOM);
        smallText.setForeground(SECONDARY_TEXT_COLOR);
        smallText.setBorder(BorderFactory.createEmptyBorder(0, TEXT_PADDING_LEFT, 0, TEXT_PADDING_RIGHT));
        smallText.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        16
                )
        );
        operations = smallText;

        JLabel text = new JLabel("0");
        text.setHorizontalAlignment(SwingConstants.RIGHT);
        text.setVerticalAlignment(SwingConstants.TOP);
        text.setForeground(MAIN_TEXT_COLOR);
        text.setBorder(BorderFactory.createEmptyBorder(0, TEXT_PADDING_LEFT, 0, TEXT_PADDING_RIGHT));
        text.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        22
                )
        );
        values = text;

        smallTextContainer.add(smallText);
        Textcontainer.add(text);

        container.add(smallTextContainer);
        container.add(Textcontainer);
        c.add(container);

        textFieldLayout.addLayoutComponent(smallTextContainer, BorderLayout.NORTH);
        textFieldLayout.addLayoutComponent(Textcontainer, BorderLayout.NORTH);

        return container;
    }

    private Component GenerateButtonField(Container c) {

        GridLayout buttonFieldLayout = new GridLayout(BUTTONS.length, 1);
        CustomJPanel container = new CustomJPanel(
                new Dimension(PANEL_WIDTH, 0),
                Color.blue,
                buttonFieldLayout
        );

        int buttonHeight = (PANEL_HEIGHT - TITLE_HEIGHT - TEXTFIELD_HEIGHT) / BUTTONS.length;
        int buttonWidth = PANEL_WIDTH / BUTTONS[0].length;

        for (int i = 0; i < BUTTONS.length; i++) {
            CustomJPanel line = new CustomJPanel(new Dimension(PANEL_WIDTH, buttonHeight), Color.yellow, new GridBagLayout());
            for (int j = 0; j < BUTTONS[0].length; j++) {

                CustomButton button = new CustomButton(
                        BUTTONS[i][j],
                        BUTTONS_SIZE[i][j],
                        new Dimension(buttonWidth, buttonHeight),
                        SECONDARY_TEXT_COLOR,
                        INPUT_BUTTON_COLOR,
                        MAIN_TEXT_COLOR,
                        INPUT_BUTTON_HOVER_COLOR
                );
                if (button.GetButtonText() == "=") {

                    button.SetBackgroundColor(EQUALS_BUTTON_COLOR);
                    button.SetBackgroundHoverColor(EQUALS_BUTTON_HOVER_COLOR);

                }
                if (button.GetButtonText() == "MOD") {

                    button.addMouseListener(new ButtonEvent(ButtonEvent.Tasks.ACTIVATE, button));

                } else if (button.GetButtonText() == "RU" || button.GetButtonText() == "RD") {

                    ButtonEvent e = new ButtonEvent(ButtonEvent.Tasks.ACTIVATE, button);
                    button.addMouseListener(e);

                    if (button.GetButtonText() == "RU") {
                        e.SetPair("RD");
                    } else {
                        e.SetPair("RU");
                    }

                } else {
                    button.addMouseListener(new ButtonEvent(ButtonEvent.Tasks.INPUT, button));
                }
                button.setBorder(BorderFactory.createLineBorder(MAIN_COLOR, 1));
                button.SetTextBorder(BorderFactory.createLineBorder(SECONDARY_COLOR, 2));
                line.add(button);
            }
            container.add(line);
        }

        c.add(container);
        return container;
    }

    public static GUI GetInstance() {
        if (instance == null) {
            instance = new GUI();
        }
        return instance;
    }

    public void UpdateTextFields(String result, String input) {

        if (IsEnoughSpace(values, "..." + result)) {
            values.setText(result);
        } else {
            values.setText("..." + result.substring(GetRemoveDB(values, result)));
        }

        if (IsEnoughSpace(operations, "..." + input)) {
            operations.setText(input);
        } else {
            operations.setText("..." + input.substring(GetRemoveDB(operations, input)));
        }

    }

    public void UpdateResultField(String result) {

        if (IsEnoughSpace(values, "..." + result)) {
            values.setText(result);
        } else {
            values.setText("..." + result.substring(GetRemoveDB(values, result)));
        }

    }

    public void UpdateInputField(String input) {

        if (IsEnoughSpace(operations, "..." + input)) {
            operations.setText(input);
        } else {
            operations.setText("..." + input.substring(GetRemoveDB(operations, input)));
        }

    }

    public void UpdateQuadraticInputField(String a, String b, String c) {

        String input = "A: "+a+"  B: "+b+"  C: "+c;
        if (IsEnoughSpace(operations, "..." + input)) {
            operations.setText(input);
        } else {
            operations.setText("..." + input.substring(GetRemoveDB(operations, input)));
        }

    }

    private boolean IsEnoughSpace(JLabel label, String text) {
        FontMetrics fontMetrics = label.getFontMetrics(label.getFont());
        int stringWidth = fontMetrics.stringWidth(text);
        return stringWidth <= label.getWidth() - TEXT_PADDING_RIGHT - TEXT_PADDING_LEFT;
    }

    private int GetRemoveDB(JLabel label, String text) {
        if (!IsEnoughSpace(label, "..." + text)) {
            String newText = text;
            int db = 0;
            while (!IsEnoughSpace(label, "..." + newText)) {
                db++;
                newText = newText.substring(1);
            }
            return db;
        }
        return 0;
    }

    public String GetResultText() {
        return values.getText();
    }

    public String GetInputText() {
        return operations.getText();
    }

}
