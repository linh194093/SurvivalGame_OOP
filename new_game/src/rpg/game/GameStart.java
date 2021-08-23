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
                    "Nhom xx: " + "\n- Đinh Tấn Minh" + "\n- Trần Doãn Hiệp" + "\n- Nguyễn Văn Linh"
                            + "\n- Dương Văn Tuyển" + "\n- Đào Minh Tiến" + "\n GVHD: Nguyễn Mạnh Tuấn",
                    "Information of us", JOptionPane.INFORMATION_MESSAGE);
        }

        if (btn == question_mark) {
            JOptionPane.showMessageDialog(null,
                    " Một ngày nọ, tại một dân làng bình yên, bỗng có 3 quái vật rồng trong truyền thuyết\n"
                            + " dẫn theo đoàn quân đến đánh chiếm ngôi làng. Ngôi làng sơ sác, mọi người bỏ chạy,\n"
                            + " nhưng còn sót lại một anh hùng với khát vọng mạnh mẽ mong muốn mang lại tự do nơi đất\n"
                            + " thiêng này cho dân làng. Để có thể lấy lại được ngôi làng, người anh hùng ấy cần đánh bại\n"
                            + " được 3 quái vật rồng hay còn gọi là Boss đang canh cửa 3 khu vực. Nếu giết được 3 Boss,\n"
                            + " dân làng sẽ được bình yên trở về, nhưng nếu không thì kết cục sẽ rất thảm hại.\n"
                            + " MỌI NGƯỜI HÃY CÙNG ĐỒNG HÀNH VỚI NGƯỜI ANH HÙNG ĐÓ NHÉ\n"
                            + "+ Nhấn các phím mũi tên để di chuyển.\n+ Nhấn A để đánh quái"
                            + "\n+ Giết quái nhỏ để lấy item, Nhấn E để nhặt item"
                            + "\n+ Giết Boss để có thể kích hoạt cổng qua màn"
                            + "\n+ Người chơi dành chiến thắng khi có thể giết được Boss ở màn cuối",
                    "Tutorial", JOptionPane.PLAIN_MESSAGE);
        }
    }

}
