// int型固定長キュー

public class StringQueue {
	private String[] que;			// キュー用の配列
	private int capacity;		// キューの容量
	private int front;			// 先頭要素カーソル
	private int rear;				// 末尾要素カーソル
	private int num;				// 現在のデータ数

	//--- 実行時例外：キューが空 ---//
	public class EmptyStringQueueException extends RuntimeException {
		public EmptyStringQueueException() { }
	}

	//--- 実行時例外：キューが満杯 ---//
	public class OverflowIntQueueException extends RuntimeException {
		public OverflowIntQueueException() { }
	}

	//--- コンストラクタ ---//
	public StringQueue(int maxlen) {
		num = front = rear = 0;
		capacity = maxlen;
		try {
			que = new String[capacity];				// キュー本体用の配列を生成
		} catch (OutOfMemoryError e) {		// 生成できなかった
			capacity = 0;
		}
	}

	//--- キューにデータをエンキュー ---//
	public String enque(String str) throws OverflowIntQueueException {
		if (num >= capacity)
			throw new OverflowIntQueueException();			// キューは満杯
		que[rear++] = str;
		num++;
		if (rear == capacity)
			rear = 0;
		return str;
	}

	//--- キューからデータをデキュー ---//
	public String deque() throws EmptyStringQueueException {
		if (num <= 0)
			throw new EmptyStringQueueException();			// キューは空
		String str = que[front++];
		num--;
		if (front == capacity)
			front = 0;
		return str;
	}

	//--- キューからデータをピーク（先頭データを覗く） ---//
	public String peek() throws EmptyStringQueueException {
		if (num <= 0)
			throw new EmptyStringQueueException();			// キューは空
		return que[front];
	}

	//--- キューを空にする ---//
	public void clear() {
		num = front = rear = 0;
	}

	//--- キューからxを探してインデックス（見つからなければ-1）を返す ---//
	public int indexOf(String str) {
		for (int i = 0; i < num; i++) {
			int idx = (i + front) % capacity;
			if (que[idx].equals(str))				// 探索成功
				return idx;
		}
		return -1;									// 探索失敗
	}

	//--- キューの容量を返す ---//
	public int getCapacity() {
		return capacity;
	}

	//--- キューに蓄えられているデータ数を返す ---//
	public int size() {
		return num;
	}

	//--- キューは空であるか ---//
	public boolean isEmpty() {
		return num <= 0;
	}

	//--- キューは満杯であるか ---//
	public boolean isFull() {
		return num >= capacity;
	}

	//--- キュー内の全データを先頭→末尾の順に表示 ---//
	public void dump() {
		if (num <= 0)
			System.out.println("キューは空です。");
		else {
			for (int i = 0; i < num; i++)
				System.out.print(que[(i + front) % capacity] + " ");
			System.out.println();
		}
	}
}
