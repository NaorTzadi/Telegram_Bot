package org.example;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.plaf.basic.BasicRadioButtonUI;
import java.awt.*;
import java.awt.event.ActionListener;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
public class Menu extends JFrame {
    TelegramBot telegramBot=new TelegramBot();
    public static List<JRadioButton> chosenOptions=new ArrayList<>();
    List<JLabel> historyLabels=new ArrayList<>();
    private final int MAX_SELECTIONS = 3;

    public Menu(){
        try {UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {e.printStackTrace();}

        this.setSize(Constants.MENU_FRAME_WIDTH,Constants.MENU_FRAME_HEIGHT);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setTitle("telegram-bot");
        this.setLayout(null);
        this.setResizable(false);
        this.setFocusable(true);

        ImageIcon telegramLogo=new ImageIcon(Constants.MENU_IMAGE_LOGO_PATH);
        this.setIconImage(telegramLogo.getImage());

        ImageIcon menuBackground=new ImageIcon(Constants.MENU_IMAGE_BACKGROUND_PATH);
        Image image=menuBackground.getImage().getScaledInstance(this.getWidth(),this.getHeight(),Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(image);
        JLabel background = new JLabel(scaledIcon);
        background.setBounds(0,0,this.getWidth(),this.getHeight());
        this.setContentPane(background);

        JLabel greetingLabel=new JLabel(Utility.getGreeting());
        greetingLabel.setBounds(10,10,300,30);
        greetingLabel.setFont(new Font("Ariel Rounded MT Bold",Font.BOLD,12));
        this.add(greetingLabel);
        JLabel timeLabel=new JLabel(Utility.getFormattedTime());
        timeLabel.setBounds(270,10,100,30);
        timeLabel.setFont(new Font("Ariel Rounded MT Bold",Font.BOLD,12));
        this.add(timeLabel);

        JLabel robotName=new JLabel("hello! my name is "+telegramBot.getBotUsername());
        robotName.setBounds(Constants.MENU_FRAME_WIDTH/20,50,300,30);
        robotName.setFont(new Font("Baskerville Old Face",Font.BOLD,16));
        this.add(robotName);
        JLabel searchForMeLabel=new JLabel("search for me on telegram");
        searchForMeLabel.setBounds(20,70,300,30);
        searchForMeLabel.setFont(new Font("Baskerville Old Face",Font.BOLD,16));
        this.add(searchForMeLabel);

        JButton button1=new JButton("user statistics");
        button1.setBounds(20,130,Constants.MENU_BUTTON_OPTION_WIDTH,Constants.MENU_COMPONENT_STANDARD_HEIGHT);
        this.add(button1);

        JButton button2=new JButton("users activity history: ");
        button2.setBounds(20,180,Constants.MENU_BUTTON_OPTION_WIDTH,Constants.MENU_COMPONENT_STANDARD_HEIGHT);
        this.add(button2);

        JButton button3=new JButton("create a graph");
        button3.setBounds(20,230,Constants.MENU_BUTTON_OPTION_WIDTH,Constants.MENU_COMPONENT_STANDARD_HEIGHT);
        this.add(button3);

        JButton returnButton=new JButton("return");
        returnButton.setVisible(false);
        returnButton.setBounds(20,500,100,60);
        this.add(returnButton);

        List<JLabel> statisticsLabels=new ArrayList<>();
        JLabel userStatisticsLabel1=new JLabel("requests received: ");    statisticsLabels.add(userStatisticsLabel1);
        JLabel userStatisticsLabel2=new JLabel("users received: ");       statisticsLabels.add(userStatisticsLabel2);
        JLabel userStatisticsLabel3=new JLabel("most active user: ");     statisticsLabels.add(userStatisticsLabel3);
        JLabel userStatisticsLabel4=new JLabel("most popular action: ");  statisticsLabels.add(userStatisticsLabel4);
        JLabel userStatisticsLabel5=new JLabel("least active user: ");    statisticsLabels.add(userStatisticsLabel5);
        JLabel userStatisticsLabel6=new JLabel("least popular action: "); statisticsLabels.add(userStatisticsLabel6);

        int addHeight=0;
        for(JLabel label:statisticsLabels){
            label.setBounds(50,150+addHeight,330,30);
            label.setVisible(false);
            label.setFont(new Font("Ariel Rounded MT Bold",Font.BOLD,12));
            this.add(label);
            addHeight+=50;
        }

        List<JRadioButton> optionButtons=new ArrayList<>();
        JRadioButton option1Button=new JRadioButton(Constants.JOKES_OPTION);     optionButtons.add(option1Button);
        JRadioButton option2Button=new JRadioButton(Constants.NEWS_OPTION);      optionButtons.add(option2Button);
        JRadioButton option3Button=new JRadioButton(Constants.NUMBERS_OPTION);   optionButtons.add(option3Button);
        JRadioButton option4Button=new JRadioButton(Constants.NASA_OPTION);      optionButtons.add(option4Button);
        JRadioButton option5Button=new JRadioButton(Constants.COUNTRIES_OPTION); optionButtons.add(option5Button);

        int optionButtonHeight=300;
        for(JRadioButton button:optionButtons){
            button.setBounds(50,optionButtonHeight,100,30);
            button.setOpaque(false);
            button.setIcon(new CustomRadioButtonIcon());
            button.setUI(new BasicRadioButtonUI());
            button.setBorder(null);
            this.add(button);
            optionButtonHeight+=50;
        }
        List<JRadioButton> selectedButtons = new ArrayList<>();
        ActionListener actionListener = e -> {
            JRadioButton sourceButton = (JRadioButton) e.getSource();
            if (sourceButton.isSelected()) {
                if (selectedButtons.size() >= MAX_SELECTIONS) {
                    JRadioButton oldestSelectedButton = selectedButtons.get(0);
                    oldestSelectedButton.setSelected(false);
                    selectedButtons.remove(oldestSelectedButton);
                    chosenOptions.remove(oldestSelectedButton);
                }
                selectedButtons.add(sourceButton);
                chosenOptions.add(sourceButton);
            } else {
                selectedButtons.remove(sourceButton);
                chosenOptions.remove(sourceButton);
            }
        };

        option1Button.addActionListener(actionListener);
        option2Button.addActionListener(actionListener);
        option3Button.addActionListener(actionListener);
        option4Button.addActionListener(actionListener);
        option5Button.addActionListener(actionListener);

        List<Component> mainComponents=new ArrayList<>();
        mainComponents.add(robotName);
        mainComponents.add(searchForMeLabel);
        mainComponents.add(button1);
        mainComponents.add(button2);
        mainComponents.add(button3);
        mainComponents.add(option1Button);
        mainComponents.add(option2Button);
        mainComponents.add(option3Button);
        mainComponents.add(option4Button);
        mainComponents.add(option5Button);

        JLabel connectionLabel=new JLabel("check your internet connection...");
        connectionLabel.setBounds(50,100,350,50);
        connectionLabel.setFont(new Font("Baskerville Old Face",Font.BOLD,20));
        connectionLabel.setVisible(false);
        this.add(connectionLabel);

        JLabel disconnectedIcon=new JLabel();
        disconnectedIcon.setBounds(50,200,300,200);
        ImageIcon instructionsIcon3=new ImageIcon(Constants.MENU_IMAGE_DISCONNECTED_PATH);
        Image disconnectedImage=instructionsIcon3.getImage();
        Image scaledConstructionsImage3=disconnectedImage.getScaledInstance(disconnectedIcon.getWidth(),disconnectedIcon.getHeight(),disconnectedImage.SCALE_SMOOTH);
        ImageIcon scaledConstructionsIcon3=new ImageIcon(scaledConstructionsImage3);
        disconnectedIcon.setIcon(scaledConstructionsIcon3);
        disconnectedIcon.setVisible(false);
        this.add(disconnectedIcon);

        JButton connectionButton=new JButton("check again");
        connectionButton.setBounds(100,450,200,50);
        connectionButton.setVisible(false);
        this.add(connectionButton);

        connectionButton.addActionListener(e -> {
            boolean isConnected=Utility.isConnected();
            for(Component component:mainComponents){
                component.setVisible(isConnected);}
            connectionLabel.setVisible(!isConnected);
            disconnectedIcon.setVisible(!isConnected);
            connectionButton.setVisible(!isConnected);
        });
        connectionButton.doClick();


        button1.addActionListener(e -> {
            hideMainComponents(mainComponents);
            userStatisticsLabel1.setText("requests received: "+ UsersStatistics.getSumOfRequests());
            userStatisticsLabel2.setText("users received: "+ UsersStatistics.getActiveUsers().size());
            userStatisticsLabel3.setText("most active user: "+ UsersStatistics.getMostCommon(UsersStatistics.getActiveUsers()));
            userStatisticsLabel4.setText("most popular action: "+ UsersStatistics.getMostCommon(UsersStatistics.getRequests()));
            if(!UsersStatistics.getMostCommon(UsersStatistics.getActiveUsers()).equals(UsersStatistics.getLeastCommon(UsersStatistics.getActiveUsers()))){userStatisticsLabel5.setText("least active user: "+ UsersStatistics.getLeastCommon(UsersStatistics.getActiveUsers()));}
            if(!UsersStatistics.getMostCommon(UsersStatistics.getRequests()).equals(UsersStatistics.getLeastCommon(UsersStatistics.getRequests()))){userStatisticsLabel6.setText("least popular action: "+ UsersStatistics.getLeastCommon(UsersStatistics.getRequests()));}
            for(JLabel label:statisticsLabels){label.setVisible(true);}
            returnButton.setVisible(true);
        });
        button2.addActionListener(e -> {
            hideMainComponents(mainComponents);
            refreshHistory();
            for(JLabel label:historyLabels){label.setVisible(true);}
            returnButton.setVisible(true);
        });
        button3.addActionListener(e -> {
            JFrame graphFrame=new JFrame();
            openGraphFrame(graphFrame);
            //hideMainComponents(mainComponents);
            //returnButton.setVisible(true);
        });
        returnButton.addActionListener(e -> {
            hideStatisticsLabels(statisticsLabels);
            hideHistoryLabels(historyLabels);
            returnButton.setVisible(false);
            showMainComponents(mainComponents);
        });
        this.setVisible(true);
    }

    private static void hideStatisticsLabels(List<JLabel> button1Labels){for(JLabel label :button1Labels){label.setVisible(false);}}
    private static void hideHistoryLabels(List<JLabel> historyLabels){for(JLabel label:historyLabels){label.setVisible(false);}}
    private static void hideMainComponents(List<Component> mainComponents){for(Component component:mainComponents){component.setVisible(false);}}
    private static void showMainComponents(List<Component> mainComponents){for(Component component:mainComponents){component.setVisible(true);}}

    public void refreshHistory() {
        for (JLabel label : historyLabels) {this.remove(label);}
        historyLabels.clear();
        List<History> histories = History.getHistoryList();
        int num1=50; int num2 = 1;
        for (History history : histories) {
            JLabel label=new JLabel(num2 + ". username: " + history.username() + " activity: " + history.activity() + " date: " + history.date());
            label.setBounds(20,num1,350,30);
            label.setFont(new Font("Ariel Rounded MT Bold",Font.BOLD,12));
            num1+=40; num2++;
            historyLabels.add(label);
            this.add(label);
        }
    }

    private void openGraphFrame(JFrame graphFrame) {
        graphFrame.setTitle("Graph Frame");
        graphFrame.setSize(800, 600);
        centerFrame(graphFrame);
        try {
            URL url = new URL(UsersStatistics.getTheChartGraphUrl());
            JLabel label = new JLabel();
            label.setBounds(50,50,700,500);

            ImageIcon imageIcon = new ImageIcon(ImageIO.read(url));
            Rectangle graphLabelBounds=label.getBounds();
            Image graphImage=imageIcon.getImage();
            int graphImageWidth=graphLabelBounds.width;
            int graphImageHeight=graphLabelBounds.height;
            int originalGraphImageWidth=graphImage.getWidth(null);
            int originalGraphImageHeight=graphImage.getHeight(null);
            double scaleGraphImage=Math.min((double) graphImageWidth/ originalGraphImageWidth,(double) graphImageHeight/originalGraphImageHeight);
            int newGraphImageWidth=(int)(originalGraphImageWidth*scaleGraphImage);
            int newGraphImageHeight=(int)(originalGraphImageHeight*scaleGraphImage);
            Image scaledGraphImage=graphImage.getScaledInstance(newGraphImageWidth,newGraphImageHeight,Image.SCALE_SMOOTH);
            ImageIcon GraphLabelCover=new ImageIcon(scaledGraphImage);
            label.setIcon(GraphLabelCover);

            graphFrame.add(label);} catch (Exception e) {e.printStackTrace();}
        graphFrame.setVisible(true);
    }
    private void centerFrame(JFrame frame) {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int centerX = (int) (screenSize.getWidth() - frame.getWidth()) / 2;
        int centerY = (int) (screenSize.getHeight() - frame.getHeight()) / 2;
        frame.setLocation(centerX, centerY);
    }
}