package com.redrock.practice;

import com.badlogic.gdx.utils.Array;

import java.util.Arrays;

class Practice {
    public static  void main(String[] args) {
        int[] array = {1,3,9,4,2,7,9,10,6,2};
        float[] floatArray = {1,3,-5,7,14,10,6,2};
        int evenCount = countEvenNumbers(array);
        System.out.println("``````````````````````````````````````````````````````````");
        System.out.println("The number of even numbers in the array is : " + evenCount);
        int countResult = countPositiveNumbersDivisibleBySeven(array);
        System.out.println("Positive numbers are divisible by 7 : " + countResult );
        int maxValue = findMaxValueInArray(array);
        System.out.println("The largest value in the array is : " + maxValue);
        int firstEvenPosition = findFirstEvenValueIndex(array);
        System.out.println("The first even position is : " + firstEvenPosition);
        int positionInArray = findValueIndex(array,5);
        System.out.println("The number position in the array is : " + positionInArray);
        int[] newArray = addNumberToArray(array,4,5);
        System.out.println("array before updated is : " + Arrays.toString(newArray));
        int[] arrayBeforeDeleted = deleteEvenNumbers(array);
        System.out.println("array before deleted is : " + Arrays.toString(arrayBeforeDeleted));
        float[] floatArrayBeforeUpdate = buildArray(floatArray);
        System.out.println("array before build is : " + Arrays.toString(floatArrayBeforeUpdate));
        float[] floatArrayBeforeSort= sortNumbersInArrayDivisibleByThree(floatArray);
        System.out.println("array before sort is : " + Arrays.toString(floatArrayBeforeSort));

        int[] intArrayBeforeDelete= removeDuplicates(array);
        System.out.println("array before removeDuplicates is : " + Arrays.toString(intArrayBeforeDelete));



        System.out.println("``````````````````````````````````````````````````````````");
    }

     private static int countEvenNumbers(int[] numbers) {
        int count = 0;

        for (int number : numbers){
            if(number % 2 != 1){
                count++;
            }
        }

        return count;
    }
     public static int countPositiveNumbersDivisibleBySeven(int[] numbers) {
         int count = 0;

         for (int number : numbers) {
             if (number > 0 && number % 7 == 0) {
                 count++;
             }
         }

         return count;
     }
     public static int findMaxValueInArray(int[] numbers) {
        int maxValue = numbers[0];

        for (int number : numbers){
            if(number >  maxValue){
                maxValue = number;
            }
        }

        return maxValue;
     }
     public static int findFirstEvenValueIndex(int[] numbers) {
        for (int i = 0 ; i < numbers.length ; i++){
            if(numbers[i] % 2 != 1 && numbers[i] > 0){
                return i;
            }
        }

        return -1;
     }
     public static int findValueIndex(int[] numbers, int target) {
         for (int i = 0 ; i < numbers.length ; i++){
             if(numbers[i] == target){
                 return i;
             }
         }

         return -1;
     }
     public static int[] addNumberToArray(int[] numbers, int position, int value) {
         int[] newArray = new int[numbers.length + 1];

         for (int i = 0 ,j = 0;  i <= numbers.length ; i++){
             if(i == position){
                 newArray[i] = value;
             }
             else {
                 newArray[i] = numbers[j];
                 j++;
             }
         }

         return newArray;
     }
     public static int[] deleteEvenNumbers(int[] numbers) {
        int countIndex = 0;
        for (int number : numbers) {
            if (number % 2 == 1) {
                countIndex++;
            }
        }

        int[] newArray = new int[countIndex];
        for (int i = 0 ,j = 0; i < numbers.length ; i++){
            if(numbers[i] %2 == 1){
                newArray[j] = numbers[i];
                j++;
            }
        }

        return newArray;
    }
     public static float[] buildArray(float[] numbers) {
        int countIndex = 0;
        for (float number : numbers) {
            if (number < 0) {
                countIndex++;
            }
        }

        float[] newArray = new float[countIndex];
        for (int i = 0 ,j = 0; i < numbers.length ; i++){
            if(numbers[i] < 0){
                newArray[j] = numbers[i];
                j++;
            }
        }

        return newArray;
    }

    public static int[] removeDuplicates(int[] arrayWithDuplicates) {

        Arrays.sort(arrayWithDuplicates);
        // Tạo một mảng mới với kích thước bằng đúng số lượng phần tử duy nhất
        int uniqueCount = 1;
        for (int i = 1; i < arrayWithDuplicates.length; i++) {
            if (arrayWithDuplicates[i] != arrayWithDuplicates[i - 1]) {
                uniqueCount++;
            }
        }
        int[] uniqueArray = new int[uniqueCount];

        // Sao chép các phần tử duy nhất vào mảng mới
        uniqueArray[0] = arrayWithDuplicates[0];
        int index = 1;
        for (int i = 1; i < arrayWithDuplicates.length; i++) {
            if (arrayWithDuplicates[i] != arrayWithDuplicates[i - 1]) {
                uniqueArray[index++] = arrayWithDuplicates[i];
            }
        }
        System.out.println("uniqueArray" + uniqueArray[1]);

        return uniqueArray;
    }

    public static float[] sortNumbersInArrayDivisibleByThree(float[] numbers) {
        int countIndex = 0;
        for (float number : numbers) {
            if (number  %7 == 0) {
                countIndex++;
            }
        }
        float[] newArray = new float[numbers.length];

        for (int i = 0 ,j = 0; i < numbers.length ; i++){
            if(numbers[i] %7 == 0){
                newArray[j] = numbers[i];
                j++;
            } else {
                newArray[countIndex] = numbers[i];
                countIndex++;
            }
        }
        return newArray;
    }


 }
