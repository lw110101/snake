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
	// 地图的宽（列）
	public static final int WIDTH = 35;//
	// 地图的高（行）
	public static final int HEIGHT = 20;
	// 格子的宽
	public static final int CELLWIDTH = 20;// 单位：像素
	// 格子的高
	public static final int CELLHEIGHT = 20;
	// 地图
	private boolean[][] background = new boolean[HEIGHT][WIDTH];
	// 用集合装蛇的坐标
	LinkedList<Point> snake = new LinkedList<Point>();
	// 创建一个食物坐标类
	Point food = new Point();
	// 使用四个常量定义方向
	public static final int DERECTION_UP = 1; // 上

	public static final int DERECTION_DOWN = -1; // 下

	public static final int DERECTION_LEFT = 2;// 左

	public static final int DERECTION_RIGHT = -2;// 右
	
	public static final int DERECTION_STOP = 0;//静止移动
	// 初始蛇的方向
	int currentDirection = -2;// 默认初始位置向右
	// 蛇移动
	// 初始化地图

	public void initBackground() {
		for (int rows = 0; rows < background.length; rows++) {
			for (int cols = 0; cols < background[rows].length; cols++) {
				if (rows == 0 || rows == (HEIGHT - 1) || cols == 0 || cols == (WIDTH - 1)) { // 第一行、最后一行、 第一列与最后一列
					background[rows][cols] = true;
				}
			}
		}
	}

	// 初始化蛇
	public void initSnake() {// point是坐标类

		int x = WIDTH / 2;
		int y = HEIGHT / 2;
		snake.addFirst(new Point(x - 1, y));
		snake.addFirst(new Point(x, y));
		snake.addFirst(new Point(x + 1, y));

	}

	// 初始化食物
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

	// 蛇移动
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
			// 移除蛇尾
			if (currentDirection != DERECTION_STOP) {
				if (eat()) {
					creatFood();
				} else {
					snake.removeLast();
				}
			}
		}
	}

	// 蛇改变方向
	public void changeDerection(int newDirection) {
		if (newDirection+currentDirection!=0) {
			this.currentDirection = newDirection;
		}else {
			this.currentDirection =DERECTION_STOP ;
		}
	}
	
	// 蛇吃东西
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

	// 游戏结束
	public boolean isGameOver() {
		// 撞墙死亡
		Point head = snake.getFirst();
		if (background[head.y][head.x] == true) {
			return true;
		}
		// 咬到自己 蛇身
		for (int i = 1; i < snake.size(); i++) {
			Point body = snake.get(i);
			if (head.equals(body)) {
				return true;
			}
		}
		return false;
	}

	// 画图
	public void paint(Graphics g) {
		// 画地图
		for (int rows = 0; rows < background.length; rows++) { // rows =0
			for (int cols = 0; cols < background[rows].length; cols++) {

				if (background[rows][cols]) {
					// 石头
					g.setColor(Color.GRAY);
				} else {
					// 安全区域
					g.setColor(Color.WHITE);
				}
				g.fill3DRect(cols * CELLWIDTH, rows * CELLHEIGHT, CELLWIDTH, CELLHEIGHT, true);
			}
		}
		
		// 画蛇身
		g.setColor(Color.GREEN);
		for (int i = 1; i < snake.size(); i++) {
			Point body = snake.get(i);
			g.fill3DRect(body.x * CELLWIDTH, body.y * CELLHEIGHT, CELLWIDTH, CELLHEIGHT, true);
		}
		// 画蛇-----蛇头
		Point head = snake.getFirst();
		g.setColor(Color.RED);
		g.fill3DRect(head.x * CELLWIDTH, head.y * CELLHEIGHT, CELLWIDTH, CELLHEIGHT, true);
		// 画食物
		g.setColor(Color.BLUE);
		g.fill3DRect(food.x * CELLWIDTH, food.y * CELLHEIGHT, CELLWIDTH, CELLHEIGHT, true);
		//画字
		if(isGameOver()) {
			g.setColor(Color.RED);
			g.setFont(new Font("楷体", Font.BOLD, 35));
			g.drawString("GAME OVER!",(WIDTH * CELLWIDTH -100)/2, (HEIGHT * CELLHEIGHT )/2);
		}	
	}

	public static void main(String[] args) {

		JFrame frame = new JFrame("贪吃蛇-v2");
		SnakeGame2 snakeView = new SnakeGame2();
		//初始化地图
		snakeView.initBackground();
		//初始化蛇的信息
		snakeView.initSnake();
		//生成食物
		snakeView.creatFood();
		
		frame.add(snakeView);
		//初始化窗口
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
				
				// 移动
				snakeView.move();	
				
				// 重画游戏		
				snakeView.repaint();// 调用repaint方法的时候实际上就是调用了paint方法。
				if(snakeView.isGameOver()) {
					if(e.getKeyChar()=='\n') {
						System.exit(0);
					}
				}
			}
		});
			
	}
}
