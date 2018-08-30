public class threeSum {
	public static boolean threeSum(int[] nums) {
		if (isInArray(nums, 0)) {
			return true;
		} else {
			for (int i = 0; i < nums.length; i = i + 1) {
				for (int j = 0; j < nums.length; j = j + 1) {
					int sum;
					sum = nums[i] + nums[j];
					if (isInArray(nums, sum * -1)) {
						return true;
					}
				}
			}
			return false;
		}
	}
	public static boolean isInArray(int[] nums, int target) {
		for (int i = 0; i < nums.length; i = i + 1) {
			if (target == nums[i]) {
				return true;
			}
		}
		return false;
	}
} 
