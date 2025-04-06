package wars;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.*;

/**
 * Provide a GUI interface for the game
 * 
 * @author A.A.Marczyk
 * @version 20/02/12
 */
public class GameGUI 
{
    private BATHS gp = new SeaBattles("Fred");
    private JFrame myFrame = new JFrame("Game GUI");
    private Container contentPane = myFrame.getContentPane();
    private JTextArea listing = new JTextArea();
    private JLabel codeLabel = new JLabel ();
    private JButton fightBtn = new JButton("Fight Encounter");
    private JButton viewBtn = new JButton("View State");
    private JButton clearBtn = new JButton("Clear");
    private JButton quitBtn = new JButton("Quit");
    private JPanel eastPanel = new JPanel();

    
    public GameGUI()
    {
        makeFrame();
        makeMenuBar(myFrame);
    }
    

    /**
     * Create the Swing frame and its content.
     */
    private void makeFrame()
    {    
        myFrame.setLayout(new BorderLayout());
        myFrame.add(listing,BorderLayout.CENTER);
        listing.setVisible(false);
        contentPane.add(eastPanel, BorderLayout.EAST);
        // set panel layout and add components
        eastPanel.setLayout(new GridLayout(4,1));

        eastPanel.add(viewBtn);
        viewBtn.addActionListener(new ViewStateHandler());
        
        eastPanel.add(fightBtn);
        fightBtn.addActionListener(new FightHandler());
        
        eastPanel.add(clearBtn);
        clearBtn.addActionListener(new ClearHandler());
        
        eastPanel.add(quitBtn);
        quitBtn.addActionListener(new QuitHandler());
        
        myFrame.pack();
        myFrame.setVisible(true);
    }
    
    /**
     * Create the main frame's menu bar.
     */
    private void makeMenuBar(JFrame frame)
    {
        JMenuBar menubar = new JMenuBar();
        frame.setJMenuBar(menubar);
        
        // Create the Ships menu
        JMenu shipsMenu = new JMenu("Ships");
        menubar.add(shipsMenu);        
    }


    

    
    private class ViewStateHandler implements ActionListener {
        public void actionPerformed(ActionEvent e) {            
            listing.setVisible(true);
            // Using gp.toString() for state info; adjust if you have a dedicated getGameState() method.
            String stateInfo = gp.toString();
            listing.setText(stateInfo);
        }
    }
    
    private class FightHandler implements ActionListener {
        public void actionPerformed(ActionEvent e) {            
            String input = JOptionPane.showInputDialog(myFrame, "Enter encounter number to fight:");
            if (input != null && !input.trim().isEmpty()) {
                try {
                    int encNo = Integer.parseInt(input.trim());
                    String result = gp.fightEncounter(encNo);
                    JOptionPane.showMessageDialog(myFrame, result, "Fight Encounter", JOptionPane.INFORMATION_MESSAGE);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(myFrame, "Invalid encounter number.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }
    
    private class ClearHandler implements ActionListener {
        public void actionPerformed(ActionEvent e) {            
            listing.setText("");
            listing.setVisible(false);            
        }
    }
    
    private class QuitHandler implements ActionListener {
        public void actionPerformed(ActionEvent e) {            
            System.exit(0);
        }
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable(){
            public void run() {
                new GameGUI();
            }
        });
    }
    
}
   
