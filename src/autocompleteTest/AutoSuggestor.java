package autocompleteTest;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import javax.swing.AbstractAction;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JWindow;
import javax.swing.KeyStroke;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class AutoSuggestor {
    private final JTextField textField; //keyword에서의 입력창
    private final Window container;
    private JPanel suggestionsPanel;
    private JWindow autoSuggestionPopUpWindow; //자동완성 창
    private String typedWord;
    private final ArrayList<String> dictionary = new ArrayList<>(); //단어들 모아놓은 배열들
    private int currentIndexOfSpace, tW, tH; //자동완성창 길이 조절용
    private DocumentListener documentListener = new DocumentListener() {
        @Override
        public void insertUpdate(DocumentEvent de) {
            checkForAndShowSuggestions();
        }

        @Override
        public void removeUpdate(DocumentEvent de) {
            checkForAndShowSuggestions();
        }

        @Override
        public void changedUpdate(DocumentEvent de) {
            checkForAndShowSuggestions();
        }
    };
    private final Color suggestionsTextColor;
    private final Color suggestionFocusedColor;
    
    //생성자
    public AutoSuggestor(JTextField textField, Window mainWindow, ArrayList<String> words, Color popUpBackground, Color textColor, Color suggestionFocusedColor, float opacity) {
        this.textField = textField;
        this.suggestionsTextColor = textColor;
        this.container = mainWindow;
        this.suggestionFocusedColor = suggestionFocusedColor;
        this.textField.getDocument().addDocumentListener(documentListener);

//        setDictionary(words);

        typedWord = "";
        currentIndexOfSpace = 0;
        tW = 0;
        tH = 0;

        autoSuggestionPopUpWindow = new JWindow(mainWindow); //자동완성창
        autoSuggestionPopUpWindow.setOpacity(opacity); //자동완성창의 투명도

        suggestionsPanel = new JPanel(); //해당 단어를 넣을 패널 생성
        suggestionsPanel.setLayout(new GridLayout(0, 1)); //레이아웃설정
        suggestionsPanel.setBackground(popUpBackground); //배경설청

        addKeyBindingToRequestFocusInPopUpWindow(); //팝업된 윈도우창에 대한 키 명령어 구현함수
    }

    private void addKeyBindingToRequestFocusInPopUpWindow() {
        textField.getInputMap(JComponent.WHEN_FOCUSED).put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0, true), "Down released");
        textField.getActionMap().put("Down released", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent ae) {//focuses the first label on popwindow
                for (int i = 0; i < suggestionsPanel.getComponentCount(); i++) {
                    if (suggestionsPanel.getComponent(i) instanceof SuggestionLabel) {
                        ((SuggestionLabel) suggestionsPanel.getComponent(i)).setFocused(true);
                        autoSuggestionPopUpWindow.toFront();
                        autoSuggestionPopUpWindow.requestFocusInWindow();
                        suggestionsPanel.requestFocusInWindow();
                        suggestionsPanel.getComponent(i).requestFocusInWindow();
                        break;
                    }
                }
            }
        });
        suggestionsPanel.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0, true), "Down released");
        suggestionsPanel.getActionMap().put("Down released", new AbstractAction() {
            int lastFocusableIndex = 0;

            @Override
            public void actionPerformed(ActionEvent ae) {//allows scrolling of labels in pop window (I know very hacky for now :))

                ArrayList<SuggestionLabel> sls = getAddedSuggestionLabels();
                int max = sls.size();

                if (max > 1) {//more than 1 suggestion
                    for (int i = 0; i < max; i++) {
                        SuggestionLabel sl = sls.get(i);
                        if (sl.isFocused()) {
                            if (lastFocusableIndex == max - 1) {
                                lastFocusableIndex = 0;
                                sl.setFocused(false);
                                autoSuggestionPopUpWindow.setVisible(false);
                                setFocusToTextField();
                                checkForAndShowSuggestions();//fire method as if document listener change occured and fired it

                            } else {
                                sl.setFocused(false);
                                lastFocusableIndex = i;
                            }
                        } else if (lastFocusableIndex <= i) {
                            if (i < max) {
                                sl.setFocused(true);
                                autoSuggestionPopUpWindow.toFront();
                                autoSuggestionPopUpWindow.requestFocusInWindow();
                                suggestionsPanel.requestFocusInWindow();
                                suggestionsPanel.getComponent(i).requestFocusInWindow();
                                lastFocusableIndex = i;
                                break;
                            }
                        }
                    }
                } else {//only a single suggestion was given
                    autoSuggestionPopUpWindow.setVisible(false);
                    setFocusToTextField();
                    checkForAndShowSuggestions();//fire method as if document listener change occured and fired it
                }
            }
        });
    }

    private void setFocusToTextField() {
        container.toFront();
        container.requestFocusInWindow();
        textField.requestFocusInWindow();
    }

    public ArrayList<SuggestionLabel> getAddedSuggestionLabels() { //라벨들을 패널에 붙임
        ArrayList<SuggestionLabel> sls = new ArrayList<>();
        for (int i = 0; i < suggestionsPanel.getComponentCount(); i++) {
            if (suggestionsPanel.getComponent(i) instanceof SuggestionLabel) {
                SuggestionLabel sl = (SuggestionLabel) suggestionsPanel.getComponent(i);
                sls.add(sl);
            }
        }
        return sls;
    }

    private void checkForAndShowSuggestions() {
        typedWord = getCurrentlyTypedWord();

        suggestionsPanel.removeAll();//remove previos words/jlabels that were added

        //used to calcualte size of JWindow as new Jlabels are added
        tW = 0;
        tH = 0;

        boolean added = wordTyped(typedWord);

        if (!added) {
            if (autoSuggestionPopUpWindow.isVisible()) {
                autoSuggestionPopUpWindow.setVisible(false);
            }
        } else {
            showPopUpWindow();
            setFocusToTextField();
        }
    }

    protected void addWordToSuggestions(String word) {
        SuggestionLabel suggestionLabel = new SuggestionLabel(word, suggestionFocusedColor, suggestionsTextColor, this);

        calculatePopUpWindowSize(suggestionLabel);

        suggestionsPanel.add(suggestionLabel);
    }
    //area_input한 곳 추출
    public String getCurrentlyTypedWord() {//get newest word after last white spaceif any or the first word if no white spaces
        String text = textField.getText();
        String wordBeingTyped = "";
        if (text.contains(" ")) {
            int tmp = text.lastIndexOf(" ");
            if (tmp >= currentIndexOfSpace) {
                currentIndexOfSpace = tmp;
                wordBeingTyped = text.substring(text.lastIndexOf(" "));
            }
        } else {
            wordBeingTyped = text;
        }
        return wordBeingTyped.trim();
    }

    private void calculatePopUpWindowSize(JLabel label) { //자동완성 팝업창에 들어갈 라벨에 대한 크기 설정메서드
        //so we can size the JWindow correctly
        if (tW < label.getPreferredSize().width) {
            tW = label.getPreferredSize().width;
        }
        tH += label.getPreferredSize().height;
    }

    private void showPopUpWindow() { //팝업창 보여주는 메서드 + 크기조정등
        autoSuggestionPopUpWindow.getContentPane().add(suggestionsPanel);
        autoSuggestionPopUpWindow.setMinimumSize(new Dimension(textField.getWidth(), 30));
        autoSuggestionPopUpWindow.setSize(tW, tH);
        autoSuggestionPopUpWindow.setVisible(true);

        int windowX = 0;
        int windowY = 0;

        windowX = container.getX() + textField.getX() + 5;
        if (suggestionsPanel.getHeight() > autoSuggestionPopUpWindow.getMinimumSize().height) {
            windowY = container.getY() + textField.getY() + textField.getHeight() + autoSuggestionPopUpWindow.getMinimumSize().height;
        } else {
            windowY = container.getY() + textField.getY() + textField.getHeight() + autoSuggestionPopUpWindow.getHeight();
        }

        autoSuggestionPopUpWindow.setLocation(windowX, windowY);
        autoSuggestionPopUpWindow.setMinimumSize(new Dimension(textField.getWidth(), 30));
        autoSuggestionPopUpWindow.revalidate();
        autoSuggestionPopUpWindow.repaint();

    }
    
    public void setDictionary(ArrayList<String> words) { //검색목록에 추가하는 메서드
        dictionary.clear();
        if (words == null) {
            return;//so we can call constructor with null value for dictionary without exception thrown
        }
        for (String word : words) {
            dictionary.add(word);
        }
    }

    public JWindow getAutoSuggestionPopUpWindow() {
        return autoSuggestionPopUpWindow;
    }

    public Window getContainer() {
        return container;
    }

    public JTextField getTextField() {
        return textField;
    }

    public void addToDictionary(String word) { //한단어 추가할때 쓰는 메서드
        dictionary.add(word);
    }

    boolean wordTyped(String typedWord) { //칠때마다 딕셔너리에 있는 단어와 검증하는 메서드

        if (typedWord.isEmpty()) {
            return false;
        }
        //System.out.println("Typed word: " + typedWord);

        boolean suggestionAdded = false;

        for (String word : dictionary) {//get words in the dictionary which we added
            boolean fullymatches = true;
            for (int i = 0; i < typedWord.length(); i++) {//each string in the word
                if (!typedWord.toLowerCase().startsWith(String.valueOf(word.toLowerCase().charAt(i)), i)) {//check for match
                    fullymatches = false;
                    break;
                }
            }
            if (fullymatches) {
                addWordToSuggestions(word);
                suggestionAdded = true;
            }
        }
        return suggestionAdded;
    }
}
