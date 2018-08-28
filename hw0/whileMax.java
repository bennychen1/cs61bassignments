public class whileMax {
	public static int main(String[] args) {
		int[] nums = new int[args.length];
		int count;
		count = 0;
		while (count <= args.length) {
			nums[count] = Integer.parseInt(args[count]);
		}
		return arrayMax(nums);
	}
	/** returns the largest number of an array */
	private static int arrayMax(int[] nums) {
		int max;
		max = nums[0];
		int index;
		index = 1;
		while (index < nums.length) {
			if (nums[index] > max) {
				max = nums[index];
			}
			index += 1;
		}
		return max;
	}
}