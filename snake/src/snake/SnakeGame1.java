package snake;

import java.awt.Point;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.LinkedList;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JFrame;

//将字符地图存进二维数组中
public class SnakeGame1 {
	//地图的宽
	public static final int WIDTH = 35;
	//地图的高
	public static final int HEIGHT = 9;
	
	private char[][] background = new char[HEIGHT][WIDTH];
	//用集合装蛇的坐标
	LinkedList<Point> snake=new LinkedList<Point>();
	//创建一个食物坐标类
	Point food=new Point();
	//使用四个常量定义方向
	public static final int DERECTION_UP=1;
	
	public static final int DERECTION_DOWN=-1;
	
	public static final int DERECTION_LEFT=2;
	
	public static final int DERECTION_RIGTH=-2;
	//初始蛇的方向
	int currentDerection=-2;
	// 初始化地图
	public void initBackground() {
		for (int rows = 0; rows < background.length; rows++) {
			for (int cols = 0; cols < background[rows].length; cols++) {
				if (rows == 0 || rows == (HEIGHT - 1) || cols == 0 || cols == (WIDTH - 1)) { // 第一行、最后一行、 第一列与最后一列
					background[rows][cols] = '*';
				} else {
					background[rows][cols] = ' ';
				}
			}
		}
	}

	// 显示地图的
	public void showBackground() {
		for (int rows = 0; rows < background.length; rows++) { // rows =0
			for (int cols = 0; cols < background[rows].length; cols++) {
				System.out.print(background[rows][cols]);
			}
			System.out.println(); // 换行
		}
	}
	//初始化蛇
	public void initSnake() {//point是坐标类
		
		int x=WIDTH/2;
		int y=HEIGHT/2;
		snake.addFirst(new Point(x-1,y));
		snake.addFirst(new Point(x,y));
		snake.addFirst(new Point(x+1,y));
		
	}
	//显示蛇
	public void showSnake() {
		Point head=snake.getFirst();
		background[head.y][head.x]='$';
		for(int i=1;i<snake.size();i++) {
			Point body=snake.get(i);
			background[body.y][body.x]='#';
		}
	}
	//创建食物
	public void creatFood() {
		
		Random random=new Random();
		while(true) {
			int x=random.nextInt(WIDTH);
			int y=random.nextInt(HEIGHT);
			if (background[y][x] != '*' && background[y][x] != '#' && background[y][x] != '$') {
				food.setLocation(x, y);
				
				break;
			}
		}
	}
	//显示食物
	public void showFood() {
		
		background[food.y][food.x] = '@';
	}
	//刷新游戏状态
	public void reflash() {
		//清空地图信息
		initBackground();
		//刷新蛇的信息
		showSnake();
		//刷新食物
		showFood();
		//显示地图信息
		showBackground();
	}
	////蛇移动
	public void move() {
		Point head=snake.getFirst();
		switch (currentDerection) {
		case DERECTION_UP:
			snake.addFirst(new Point(head.x, head.y - 1));
					
			break;
		case DERECTION_DOWN:
			snake.addFirst(new Point(head.x, head.y + 1));
		
			break;
		case DERECTION_LEFT:
			snake.addFirst(new Point(head.x-1, head.y));
	
			break;
		case DERECTION_RIGTH:
			snake.addFirst(new Point(head.x+1, head.y ));
	
			break;
		default:
			break;
		}
		// 移除蛇尾
		if (eat()) {
			creatFood();
		} else {		
			snake.removeLast();
		}
		
	}

	// 蛇改变方向
	public void changeDerection(int newDerection) {
		if(currentDerection!=-newDerection) {
			this.currentDerection=newDerection;
		}	
	}
	//蛇吃东西
	public boolean eat() {
		Point head=snake.getFirst();
		if(head.equals(food)) {
			return true;
		}
		return false;
	}
	//游戏结束
	public boolean isGameOver() {
		//撞墙死亡
		Point head=snake.getFirst();
		if(background[head.y][head.x]=='*') {
			return true;
		}
		//咬到自己  蛇身
		for(int i=1;i<snake.size();i++) {
			Point body=snake.get(i);
			if(head.equals(body)) {
				return true;
			}
		}
		return false;
	}
	/*//蛇移动----向上
	public void moveUp() {
		//增加蛇头
		Point head=snake.getFirst();
		Point tail = snake.getLast();
		if (head.y <= tail.y) {
			snake.addFirst(new Point(head.x, head.y - 1));
			// 移除蛇尾
			snake.removeLast();
		}

	}

	// 蛇移动----向下
	public void moveDown() {
		// 增加蛇头
		Point head = snake.getFirst();
		Point tail = snake.getLast();
		if (head.y >= tail.y) {
			snake.addFirst(new Point(head.x, head.y + 1));
			// 移除蛇尾
			snake.removeLast();
		}
	}
	//蛇移动----向左
	public void moveLeft() {
		// 增加蛇头
		Point head = snake.getFirst();
		Point tail = snake.getLast();
		if (head.x <= tail.x) {
			snake.addFirst(new Point(head.x - 1, head.y));
			// 移除蛇尾
			snake.removeLast();
		}

	}
	//蛇移动----向右
	public void moveRight() {
		// 增加蛇头
		Point head = snake.getFirst();
		Point tail = snake.getLast();
		if (head.x >= tail.x) {
			snake.addFirst(new Point(head.x + 1, head.y));
			// 移除蛇尾
			snake.removeLast();
		}
	}*/
	
	public static void main(String[] args) throws Exception {
		SnakeGame1 snake=new SnakeGame1();
		//初始化地图
		snake.initBackground();
		//初始化蛇
		snake.initSnake();
		//将蛇的信息反馈到地图
		snake.showSnake();
		//生成食物
		snake.creatFood();
		//将食物的信息反馈到地图
		snake.showFood();
		//显示地图信息
		snake.showBackground();
		
		//创建按钮
		JFrame frame=new JFrame("贪吃蛇-v1");
		JButton button=new JButton("方向键");
		frame.add(button);
		FrameUtil.initFrame(frame, 400, 300);
		button.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				int code=e.getKeyCode();
				switch (code) {
				case KeyEvent.VK_UP:
					snake.changeDerection(DERECTION_UP);
					break;
				case KeyEvent.VK_DOWN:
					snake.changeDerection(DERECTION_DOWN);
					break;
				case KeyEvent.VK_LEFT:
					snake.changeDerection(DERECTION_LEFT);
					break;
				case KeyEvent.VK_RIGHT:
					snake.changeDerection(DERECTION_RIGTH);
					break;
				default:
					break;
				}
				snake.move();
				boolean isGameOver=snake.isGameOver();
				snake.reflash();
				if(isGameOver) {
					System.out.println("游戏结束!");
					System.exit(0);
				}						
			}
		});
	}
}
