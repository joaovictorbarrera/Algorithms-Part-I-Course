public class StackWithMax extends StackLinked<Double> {
    StackLinked<Double> maxStack = new StackLinked<>();
    
    public Double returnTheMaximum() {
        return maxStack.getTop();
    }
    
    public void push(Double item) {
        if(maxStack.isEmpty()) maxStack.push(item);
        else if(Double.compare(item, maxStack.getTop()) >= 0) maxStack.push(item);
        
        super.push(item);
    }

    public Double pop() {
        Double popped = super.pop();
        if(popped.equals(maxStack.getTop())) maxStack.pop();
        return popped;
    }
    
    public static void main(String[] args) {
        int N = 128;

        StackWithMax stackMax = new StackWithMax();
        System.out.println("stackMax: " + stackMax.toString());
        System.out.println("Curr Length: " + stackMax.getLength());
        System.out.println("Max: " + stackMax.returnTheMaximum());
        for(int i = 0; i < N; i++) {
            System.out.println("pushed: " + i);
            stackMax.push((double) i);
        }

        System.out.println("stackMax: " + stackMax.toString());
        System.out.println("Curr Length: " + stackMax.getLength());
        
        System.out.print("Itereable: ");
        for(double item : stackMax) {
            System.out.print(item + " ");
        }
        System.out.println();
        System.out.println("Max: " + stackMax.returnTheMaximum());

        for(int i = 0; i < N; i++) {
            System.out.println("popped: " + stackMax.pop()) ;
        }
        System.out.println("stackMax: " + stackMax.toString());
        System.out.println("Curr Length: " + stackMax.getLength());
        System.out.println("Max: " + stackMax.returnTheMaximum());
    }
}
