public class threeSumDistinct {
	public static boolean threeSumDistinct(int[] nums) {
		for (int i = 0; i < nums.length; i = i + 1) {
			for (int j = 1; j < nums.length; j = j + 1) {
				int sum;
				sum = nums[i] + nums[j];
				if (isInArray(nums, sum * -1, j + 1)) {
					return true;
				}
			}
		}
		return false;
	}
	public static boolean isInArray(int[] nums, int target, int index) {
		for (int i = index; i < nums.length; i = i + 1) {
			if (target == nums[i]) {
				return true;
			}
		}
		return false;
	}
} 
