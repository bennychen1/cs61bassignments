public class forMax {
	public static void main(String[] args) {
		int[] nums = new int[args.length];
		int count;
		count = 0;
		while (count < args.length) {
			nums[count] = Integer.parseInt(args[count]);
			count += 1;
		}
		arrayMax(nums);
	}
	/** returns the largest number of an array */
	public static int arrayMax(int[] nums) {
		int max;
		max = nums[0];
		for (int i = 1; i < nums.length; i += 1) {
			if (nums[i] > max) {
				max = nums[i];
			}
		}
		return max;
	}
}