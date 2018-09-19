package snake;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.LinkedList;
import java.util.Random;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class SnakeGame2 extends JPanel {

	private static final long serialVersionUID = 1L;
	// ��ͼ�Ŀ��У�
	public static final int WIDTH = 35;//
	// ��ͼ�ĸߣ��У�
	public static final int HEIGHT = 20;
	// ���ӵĿ�
	public static final int CELLWIDTH = 20;// ��λ������
	// ���ӵĸ�
	public static final int CELLHEIGHT = 20;
	// ��ͼ
	private boolean[][] background = new boolean[HEIGHT][WIDTH];
	// �ü���װ�ߵ�����
	LinkedList<Point> snake = new LinkedList<Point>();
	// ����һ��ʳ��������
	Point food = new Point();
	// ʹ���ĸ��������巽��
	public static final int DERECTION_UP = 1; // ��

	public static final int DERECTION_DOWN = -1; // ��

	public static final int DERECTION_LEFT = 2;// ��

	public static final int DERECTION_RIGHT = -2;// ��
	
	public static final int DERECTION_STOP = 0;//��ֹ�ƶ�
	// ��ʼ�ߵķ���
	int currentDirection = -2;// Ĭ�ϳ�ʼλ������
	// ���ƶ�
	// ��ʼ����ͼ

	public void initBackground() {
		for (int rows = 0; rows < background.length; rows++) {
			for (int cols = 0; cols < background[rows].length; cols++) {
				if (rows == 0 || rows == (HEIGHT - 1) || cols == 0 || cols == (WIDTH - 1)) { // ��һ�С����һ�С� ��һ�������һ��
					background[rows][cols] = true;
				}
			}
		}
	}

	// ��ʼ����
	public void initSnake() {// point��������

		int x = WIDTH / 2;
		int y = HEIGHT / 2;
		snake.addFirst(new Point(x - 1, y));
		snake.addFirst(new Point(x, y));
		snake.addFirst(new Point(x + 1, y));

	}

	// ��ʼ��ʳ��
	public void creatFood() {

		Random random = new Random();
		while (true) {
			int x = random.nextInt(WIDTH);
			int y = random.nextInt(HEIGHT);
			if (background[y][x] != true) {
				food.setLocation(x, y);
				break;
			}
		}
	}

	// ���ƶ�
	public void move() {
		if (!isGameOver()) {
			Point head = snake.getFirst();
			switch (currentDirection) {
			case DERECTION_UP:
				snake.addFirst(new Point(head.x, head.y - 1));

				break;
			case DERECTION_DOWN:
				snake.addFirst(new Point(head.x, head.y + 1));

				break;
			case DERECTION_LEFT:
				snake.addFirst(new Point(head.x - 1, head.y));

				break;
			case DERECTION_RIGHT:
				snake.addFirst(new Point(head.x + 1, head.y));

				break;
			case DERECTION_STOP:

				break;
			default:
				break;
			}
			// �Ƴ���β
			if (currentDirection != DERECTION_STOP) {
				if (eat()) {
					creatFood();
				} else {
					snake.removeLast();
				}
			}
		}
	}

	// �߸ı䷽��
	public void changeDerection(int newDirection) {
		if (newDirection+currentDirection!=0) {
			this.currentDirection = newDirection;
		}else {
			this.currentDirection =DERECTION_STOP ;
		}
	}
	
	// �߳Զ���
	public boolean eat() {
		Point head = snake.getFirst();
		for (int i = 1; i < snake.size(); i++) {
			Point body = snake.get(i);
			if (head.equals(food)) {
				return true;
			} else if (body.equals(food)) {
				return true;
			}
		}
		return false;
	}

	// ��Ϸ����
	public boolean isGameOver() {
		// ײǽ����
		Point head = snake.getFirst();
		if (background[head.y][head.x] == true) {
			return true;
		}
		// ҧ���Լ� ����
		for (int i = 1; i < snake.size(); i++) {
			Point body = snake.get(i);
			if (head.equals(body)) {
				return true;
			}
		}
		return false;
	}

	// ��ͼ
	public void paint(Graphics g) {
		// ����ͼ
		for (int rows = 0; rows < background.length; rows++) { // rows =0
			for (int cols = 0; cols < background[rows].length; cols++) {

				if (background[rows][cols]) {
					// ʯͷ
					g.setColor(Color.GRAY);
				} else {
					// ��ȫ����
					g.setColor(Color.WHITE);
				}
				g.fill3DRect(cols * CELLWIDTH, rows * CELLHEIGHT, CELLWIDTH, CELLHEIGHT, true);
			}
		}
		
		// ������
		g.setColor(Color.GREEN);
		for (int i = 1; i < snake.size(); i++) {
			Point body = snake.get(i);
			g.fill3DRect(body.x * CELLWIDTH, body.y * CELLHEIGHT, CELLWIDTH, CELLHEIGHT, true);
		}
		// ����-----��ͷ
		Point head = snake.getFirst();
		g.setColor(Color.RED);
		g.fill3DRect(head.x * CELLWIDTH, head.y * CELLHEIGHT, CELLWIDTH, CELLHEIGHT, true);
		// ��ʳ��
		g.setColor(Color.BLUE);
		g.fill3DRect(food.x * CELLWIDTH, food.y * CELLHEIGHT, CELLWIDTH, CELLHEIGHT, true);
		//����
		if(isGameOver()) {
			g.setColor(Color.RED);
			g.setFont(new Font("����", Font.BOLD, 35));
			g.drawString("GAME OVER!",(WIDTH * CELLWIDTH -100)/2, (HEIGHT * CELLHEIGHT )/2);
		}	
	}

	public static void main(String[] args) {

		JFrame frame = new JFrame("̰����-v2");
		SnakeGame2 snakeView = new SnakeGame2();
		//��ʼ����ͼ
		snakeView.initBackground();
		//��ʼ���ߵ���Ϣ
		snakeView.initSnake();
		//����ʳ��
		snakeView.creatFood();
		
		frame.add(snakeView);
		//��ʼ������
		FrameUtil.initFrame(frame, WIDTH * CELLWIDTH + 17, HEIGHT * CELLHEIGHT + 38);
	
		frame.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				int code = e.getKeyCode();
				switch (code) {
				case KeyEvent.VK_UP:
					snakeView.changeDerection(DERECTION_UP);
								
					break;
				case KeyEvent.VK_DOWN:
					snakeView.changeDerection(DERECTION_DOWN);
				
					break;
				case KeyEvent.VK_LEFT:
					snakeView.changeDerection(DERECTION_LEFT);
			
					break;
				case KeyEvent.VK_RIGHT:
					snakeView.changeDerection(DERECTION_RIGHT);

					break;
				default:
					break;
				}	
				
				// �ƶ�
				snakeView.move();	
				
				// �ػ���Ϸ		
				snakeView.repaint();// ����repaint������ʱ��ʵ���Ͼ��ǵ�����paint������
				if(snakeView.isGameOver()) {
					if(e.getKeyChar()=='\n') {
						System.exit(0);
					}
				}
			}
		});
			
	}
}
