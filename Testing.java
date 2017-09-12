
public class Testing {
	public static void main(String[] args) {
		int gt2 = 0;
		int lt2 = 0;
		
		for (int i = 0; i < 1000; i++) {
			int randomNum = (int) (Math.random()*2 + 1);
			
			if (randomNum >= 2) {
				gt2++;
			}
			else {
				lt2++;
			}
		}
		System.out.println(gt2);
		System.out.println(lt2);
	}
}
