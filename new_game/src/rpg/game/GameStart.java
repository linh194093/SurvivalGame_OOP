package rpg.game;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import rpg.entity.creature.npc.Npc;
import rpg.game.setting.Setting;

public class GameStart extends JFrame implements ActionListener {
    public static float MAX_WIDTH = 800;
    public static float MAX_HEIGHT = 640;
    private ImageIcon startGameBackground;
    private JPanel panel;
    private JButton newGame;
    private JButton setting, question_mark;
    private JButton aboutUs;
    public String game_mode;

    public GameStart() {

        startGameBackground = new ImageIcon("src/Assets/start_game.png");
        if (startGameBackground == null) {

        }
        try {
            setSize(851, 470);
            setVisible(true);
            setResizable(false);
            setLocationRelativeTo(null);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            try {
                panel = new JPanel() {

                    protected void paintComponent(Graphics g) {

                        super.paintComponent(g);
                        if (startGameBackground != null) {
                            g.drawImage(startGameBackground.getImage(), 0, 0, getWidth(), getHeight(), null);
                        }
                    }
                };
            } catch (Exception e) {
                System.out.println("GameLoi");
            }
            setContentPane(panel);

            this.setLayout(null);
            setControl();

        } catch (Exception e) {
            System.out.println("choi tam");
            Game game = new Game("Game", (int) MAX_WIDTH, (int) MAX_HEIGHT);
            game.start();
        }
    }

    public void setControl() {
        newGame = new JButton("Start Game");
        add(newGame);

        newGame.setSize(120, 35);
        newGame.setLocation(350, 310);
        newGame.addActionListener(this);

        setting = new JButton("Setting");
        setting.setSize(120, 35);
        setting.setLocation(350, 350);
        setting.addActionListener(this);
        add(setting);

        Font font = new Font("Comic Sans MS", Font.BOLD, 20);
        question_mark = new JButton("?");
        add(question_mark);
        question_mark.setFont(font);
        question_mark.setSize(45, 40);
        question_mark.setLocation(750, 390);
        question_mark.addActionListener(this);

        aboutUs = new JButton("About Us");
        add(aboutUs);

        aboutUs.setSize(120, 35);
        aboutUs.setLocation(350, 390);
        aboutUs.addActionListener(this);
    }

    public void actionPerformed(ActionEvent e) {
        JButton btn = (JButton) e.getSource();

        if (btn == newGame) {
            this.dispose();
            Game game = new Game("Game", (int) MAX_WIDTH, (int) MAX_HEIGHT);
            Npc.setK(Setting.level + 1);
            game.start();
        }
        if (btn == setting) {
            this.dispose();
            new Setting();
        }

        if (btn == aboutUs) {
            JOptionPane.showMessageDialog(null,
                    "Nhom xx: " + "\n- ??inh T???n Minh" + "\n- Tr???n Do??n Hi???p" + "\n- Nguy???n V??n Linh"
                            + "\n- D????ng V??n Tuy???n" + "\n- ????o Minh Ti???n" + "\n GVHD: Nguy???n M???nh Tu???n",
                    "Information of us", JOptionPane.INFORMATION_MESSAGE);
        }

        if (btn == question_mark) {
            JOptionPane.showMessageDialog(null,
                    " M???t ng??y n???, t???i m???t d??n l??ng b??nh y??n, b???ng c?? 3 qu??i v???t r???ng trong truy???n thuy???t\n"
                            + " d???n theo ??o??n qu??n ?????n ????nh chi???m ng??i l??ng. Ng??i l??ng s?? s??c, m???i ng?????i b??? ch???y,\n"
                            + " nh??ng c??n s??t l???i m???t anh h??ng v???i kh??t v???ng m???nh m??? mong mu???n mang l???i t??? do n??i ?????t\n"
                            + " thi??ng n??y cho d??n l??ng. ????? c?? th??? l???y l???i ???????c ng??i l??ng, ng?????i anh h??ng ???y c???n ????nh b???i\n"
                            + " ???????c 3 qu??i v???t r???ng hay c??n g???i l?? Boss ??ang canh c???a 3 khu v???c. N???u gi???t ???????c 3 Boss,\n"
                            + " d??n l??ng s??? ???????c b??nh y??n tr??? v???, nh??ng n???u kh??ng th?? k???t c???c s??? r???t th???m h???i.\n"
                            + " M???I NG?????I H??Y C??NG ?????NG H??NH V???I NG?????I ANH H??NG ???? NH??\n"
                            + "+ Nh???n c??c ph??m m??i t??n ????? di chuy???n.\n+ Nh???n A ????? ????nh qu??i"
                            + "\n+ Gi???t qu??i nh??? ????? l???y item, Nh???n E ????? nh???t item"
                            + "\n+ Gi???t Boss ????? c?? th??? k??ch ho???t c???ng qua m??n"
                            + "\n+ Ng?????i ch??i d??nh chi???n th???ng khi c?? th??? gi???t ???????c Boss ??? m??n cu???i",
                    "Tutorial", JOptionPane.PLAIN_MESSAGE);
        }
    }

}
