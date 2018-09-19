package snake;

import java.awt.Point;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.LinkedList;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JFrame;

//���ַ���ͼ�����ά������
public class SnakeGame1 {
	//��ͼ�Ŀ�
	public static final int WIDTH = 35;
	//��ͼ�ĸ�
	public static final int HEIGHT = 9;
	
	private char[][] background = new char[HEIGHT][WIDTH];
	//�ü���װ�ߵ�����
	LinkedList<Point> snake=new LinkedList<Point>();
	//����һ��ʳ��������
	Point food=new Point();
	//ʹ���ĸ��������巽��
	public static final int DERECTION_UP=1;
	
	public static final int DERECTION_DOWN=-1;
	
	public static final int DERECTION_LEFT=2;
	
	public static final int DERECTION_RIGTH=-2;
	//��ʼ�ߵķ���
	int currentDerection=-2;
	// ��ʼ����ͼ
	public void initBackground() {
		for (int rows = 0; rows < background.length; rows++) {
			for (int cols = 0; cols < background[rows].length; cols++) {
				if (rows == 0 || rows == (HEIGHT - 1) || cols == 0 || cols == (WIDTH - 1)) { // ��һ�С����һ�С� ��һ�������һ��
					background[rows][cols] = '*';
				} else {
					background[rows][cols] = ' ';
				}
			}
		}
	}

	// ��ʾ��ͼ��
	public void showBackground() {
		for (int rows = 0; rows < background.length; rows++) { // rows =0
			for (int cols = 0; cols < background[rows].length; cols++) {
				System.out.print(background[rows][cols]);
			}
			System.out.println(); // ����
		}
	}
	//��ʼ����
	public void initSnake() {//point��������
		
		int x=WIDTH/2;
		int y=HEIGHT/2;
		snake.addFirst(new Point(x-1,y));
		snake.addFirst(new Point(x,y));
		snake.addFirst(new Point(x+1,y));
		
	}
	//��ʾ��
	public void showSnake() {
		Point head=snake.getFirst();
		background[head.y][head.x]='$';
		for(int i=1;i<snake.size();i++) {
			Point body=snake.get(i);
			background[body.y][body.x]='#';
		}
	}
	//����ʳ��
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
	//��ʾʳ��
	public void showFood() {
		
		background[food.y][food.x] = '@';
	}
	//ˢ����Ϸ״̬
	public void reflash() {
		//��յ�ͼ��Ϣ
		initBackground();
		//ˢ���ߵ���Ϣ
		showSnake();
		//ˢ��ʳ��
		showFood();
		//��ʾ��ͼ��Ϣ
		showBackground();
	}
	////���ƶ�
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
		// �Ƴ���β
		if (eat()) {
			creatFood();
		} else {		
			snake.removeLast();
		}
		
	}

	// �߸ı䷽��
	public void changeDerection(int newDerection) {
		if(currentDerection!=-newDerection) {
			this.currentDerection=newDerection;
		}	
	}
	//�߳Զ���
	public boolean eat() {
		Point head=snake.getFirst();
		if(head.equals(food)) {
			return true;
		}
		return false;
	}
	//��Ϸ����
	public boolean isGameOver() {
		//ײǽ����
		Point head=snake.getFirst();
		if(background[head.y][head.x]=='*') {
			return true;
		}
		//ҧ���Լ�  ����
		for(int i=1;i<snake.size();i++) {
			Point body=snake.get(i);
			if(head.equals(body)) {
				return true;
			}
		}
		return false;
	}
	/*//���ƶ�----����
	public void moveUp() {
		//������ͷ
		Point head=snake.getFirst();
		Point tail = snake.getLast();
		if (head.y <= tail.y) {
			snake.addFirst(new Point(head.x, head.y - 1));
			// �Ƴ���β
			snake.removeLast();
		}

	}

	// ���ƶ�----����
	public void moveDown() {
		// ������ͷ
		Point head = snake.getFirst();
		Point tail = snake.getLast();
		if (head.y >= tail.y) {
			snake.addFirst(new Point(head.x, head.y + 1));
			// �Ƴ���β
			snake.removeLast();
		}
	}
	//���ƶ�----����
	public void moveLeft() {
		// ������ͷ
		Point head = snake.getFirst();
		Point tail = snake.getLast();
		if (head.x <= tail.x) {
			snake.addFirst(new Point(head.x - 1, head.y));
			// �Ƴ���β
			snake.removeLast();
		}

	}
	//���ƶ�----����
	public void moveRight() {
		// ������ͷ
		Point head = snake.getFirst();
		Point tail = snake.getLast();
		if (head.x >= tail.x) {
			snake.addFirst(new Point(head.x + 1, head.y));
			// �Ƴ���β
			snake.removeLast();
		}
	}*/
	
	public static void main(String[] args) throws Exception {
		SnakeGame1 snake=new SnakeGame1();
		//��ʼ����ͼ
		snake.initBackground();
		//��ʼ����
		snake.initSnake();
		//���ߵ���Ϣ��������ͼ
		snake.showSnake();
		//����ʳ��
		snake.creatFood();
		//��ʳ�����Ϣ��������ͼ
		snake.showFood();
		//��ʾ��ͼ��Ϣ
		snake.showBackground();
		
		//������ť
		JFrame frame=new JFrame("̰����-v1");
		JButton button=new JButton("�����");
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
					System.out.println("��Ϸ����!");
					System.exit(0);
				}						
			}
		});
	}
}
