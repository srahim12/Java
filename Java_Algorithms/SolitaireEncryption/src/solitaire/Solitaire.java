package solitaire;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;
import java.util.Random;
import java.util.NoSuchElementException;

/**
 * This class implements a simplified version of Bruce Schneier's Solitaire Encryption algorithm.
 * 
 * @author RU NB CS112
 */
public class Solitaire {
	
	/**
	 * Circular linked list that is the deck of cards for encryption
	 */
	CardNode deckRear;
	
	/**
	 * Makes a shuffled deck of cards for encryption. The deck is stored in a circular
	 * linked list, whose last node is pointed to by the field deckRear
	 */
	public void makeDeck() {
		// start with an array of 1..28 for easy shuffling
		int[] cardValues = new int[28];
		// assign values from 1 to 28
		for (int i=0; i < cardValues.length; i++) {
			cardValues[i] = i+1;
		}
		
		// shuffle the cards
		Random randgen = new Random();
 	        for (int i = 0; i < cardValues.length; i++) {
	            int other = randgen.nextInt(28);
	            int temp = cardValues[i];
	            cardValues[i] = cardValues[other];
	            cardValues[other] = temp;
	        }
	     
	    // create a circular linked list from this deck and make deckRear point to its last node
	    CardNode cn = new CardNode();
	    cn.cardValue = cardValues[0];
	    cn.next = cn;
	    deckRear = cn;
	    for (int i=1; i < cardValues.length; i++) {
	    	cn = new CardNode();
	    	cn.cardValue = cardValues[i];
	    	cn.next = deckRear.next;
	    	deckRear.next = cn;
	    	deckRear = cn;
	    }
        //printList(deckRear);
	}
	
	/**
	 * Makes a circular linked list deck out of values read from scanner.
	 */
	public void makeDeck(Scanner scanner) 
	throws IOException {
		CardNode cn = null;
		if (scanner.hasNextInt()) {
			cn = new CardNode();
		    cn.cardValue = scanner.nextInt();
		    cn.next = cn;
		    deckRear = cn;
		}
		while (scanner.hasNextInt()) {
			cn = new CardNode();
	    	cn.cardValue = scanner.nextInt();
	    	cn.next = deckRear.next;
	    	deckRear.next = cn;
	    	deckRear = cn;
		}
        printList(deckRear);
	}
	
	/**
	 * Implements Step 1 - Joker A - on the deck.
	 */
	void jokerA() {
		// COMPLETE THIS METHOD


		CardNode ptr = deckRear.next;
		CardNode prev = deckRear;
		CardNode tmp;
		boolean found = false;
		while (ptr != deckRear){
			if (ptr.cardValue == 27 ){
				if(ptr == deckRear){
					tmp = prev;
					tmp.next = ptr.next;
					ptr.next = ptr.next.next;
					tmp.next.next = ptr;
					deckRear = tmp.next;
				}
				else {
					tmp = prev;
					tmp.next = ptr.next;
					ptr.next = ptr.next.next;
					tmp.next.next = ptr;
				}
				found = true;
				break;
			}
			prev = prev.next;
			ptr = ptr.next;
		}
		if (ptr == deckRear && found == false){
			deckRear = prev;
		}
        //printList(deckRear);
		return;
	}
	
	/**
	 * Implements Step 2 - Joker B - on the deck.
	 */
	void jokerB() {
	    // COMPLETE THIS METHOD
        //2,3,28,14,21,16,13,10,12,22,7,5,26,1,17,4,15,18,25,8,23,11,19,24,20,27,6,9
		CardNode ptr = deckRear.next;
		CardNode prev = deckRear;
		CardNode tmp= null,tmp2;
		boolean found = false;

        //loop until ptr is on element before rear
        while(ptr != deckRear){

            //find the second joker
			if(ptr.cardValue == 28){
    			if (ptr.next == deckRear){
                    tmp = deckRear.next;
                    prev.next = deckRear;
                    ptr.next = tmp.next;
                    tmp.next = ptr;
                    deckRear = tmp;
               	}
                else if(ptr.next.next == deckRear){
                    deckRear = ptr;
                }
				else{
                    tmp = ptr.next.next;
                    prev.next = ptr.next;
                    ptr.next = tmp.next;
                    tmp.next = ptr;
				}
                found = true;
                break;
			}
            prev = prev.next;
            ptr = ptr.next;
		}
        if (ptr == deckRear && found == false){
            tmp = ptr.next;
            prev.next = tmp;
            ptr.next = tmp.next.next;
            tmp.next.next = ptr;
            deckRear = tmp;
        }
        //printList(deckRear);
        return;
	}
	
	/**
	 * Implements Step 3 - Triple Cut - on the deck.
	 */
	void tripleCut() {
		// COMPLETE THIS METHOD

		CardNode ptr = deckRear.next, prev = deckRear, j1 = null, j2 = null, sec1Beg = deckRear.next,
				 sec1End= null, sec2Beg= null,sec2End = deckRear;
		int found = 0;
		while(ptr != deckRear){
			if(ptr.cardValue == 27 || ptr.cardValue ==28){
				if(found ==0){
					j1 = ptr;
					sec1End = prev;
					found++;
				}
				else{
					j2 = ptr;
                    sec2Beg = j2.next;
                    found++;
                    break;
				}
			}
            prev = prev.next;
            ptr = ptr.next;
    	}
        if(found == 1){
            j2 = ptr;
            sec2Beg = j2.next;

        }
        if((j2 == deckRear) && (sec1Beg == sec2Beg)){
            deckRear = sec1End;
            //printList(deckRear);
        }
        else if((sec1Beg == j1) && (j1 == sec2Beg)){
            //printList(deckRear);

            return;
        }
        else if((j1 == sec1Beg) && (sec1End == sec2End)){
            deckRear = j2;
            //printList(deckRear);
        }
        else{
            sec1End.next = sec2Beg;
            sec2End.next = j1;
            j2.next = sec1Beg;
            deckRear = sec1End;
            //printList(deckRear);
        }
        return;
	}
	void countCut(){
        CardNode ptr = deckRear.next,prev = deckRear,fHalfBeg = deckRear.next,fHalfEnd = null,sHalfBeg = null,
                        sHalfEnd = null;
        int lastCard = deckRear.cardValue;
        CardNode p = deckRear.next;
        CardNode pr = deckRear;
        for(int i = 0;i<lastCard-1;i++){
            ptr = ptr.next;
            prev = prev.next;
        }
        fHalfEnd = ptr;
        sHalfBeg = ptr.next;
        while(p != deckRear){
            p = p.next;
            pr = pr.next;
        }
        sHalfEnd = pr;
        if ((lastCard == 27) || (lastCard == 28)){
            //printList(deckRear);
        }
        else if (lastCard == 26){
            CardNode temp = prev;
            fHalfEnd.next = deckRear;
            sHalfEnd.next = fHalfBeg;
            deckRear.next = sHalfEnd;
            //printList(deckRear);
        }
        else{
            fHalfEnd.next = deckRear;
            sHalfEnd.next = fHalfBeg;
            deckRear.next = sHalfBeg;
            //printList(deckRear);
        }
        return;
	}
	
	/**
	 * Gets a key. Calls the four steps - Joker A, Joker B, Triple Cut, Count Cut, then
	 * counts down based on the value of the first card and extracts the next card value 
	 * as key. But if that value is 27 or 28, repeats the whole process (Joker A through Count Cut)
	 * on the latest (current) deck, until a value less than or equal to 26 is found, which is then returned.
	 * 
	 * @return Key between 1 and 26
	 */
	int getKey() {
		// COMPLETE THIS METHOD

        int firstCard;
        CardNode ptr = null;
        do {
            jokerA();
            jokerB();
            tripleCut();
            countCut();
            firstCard = deckRear.next.cardValue;
            if(firstCard == 28){
                firstCard--;
            }
            ptr = deckRear.next;
            for (int i = 0;i<firstCard-1;i++){
                ptr = ptr.next;
            }
        }while(ptr.next.cardValue>26);


        return ptr.next.cardValue;
	}
//1.//1 4 7 10 13 16 19 22 25 28 3 6 9 12 15 18 21 24 27 2 5 8 11 14 17 20 23 26
//jA//1 4 7 10 13 16 19 22 25 28 3 6 9 12 15 18 21 24 2 27 5 8 11 14 17 20 23 26
//jB//1 4 7 10 13 16 19 22 25 3 6 28 9 12 15 18 21 24 2 27 5 8 11 14 17 20 23 26
//tC//5 8 11 14 17 20 23 26 28 9 12 15 18 21 24 2 27 1 4 7 10 13 16 19 22 25 3 6
//cC//23 26 28 9 12 15 18 21 24 2 27 1 4 7 10 13 16 19 22 25 3 5 8 11 14 17 20 6
    //1 4 7 10 13 16 19 22 25 28 3 6 9 12 15 18 21 24 27 2 5 8 11 14 17 20 23 26
    //1 4 7 10 13 16 19 22 25 28 3 6 9 12 15 18 21 24 27 2 5 8 11 14 17 20 23 26
	
	/**
	 * Utility method that prints a circular linked list, given its rear pointer
	 * 
	 * @param rear Rear pointer
	 */
	private static void printList(CardNode rear) {
		if (rear == null) { 
			return;
		}
		System.out.print(rear.next.cardValue);
		CardNode ptr = rear.next;
		do {
			ptr = ptr.next;
			System.out.print("," + ptr.cardValue);
		} while (ptr != rear);
		System.out.println("\n");
	}

	/**
	 * Encrypts a message, ignores all characters except upper case letters
	 * 
	 * @param message Message to be encrypted
	 * @return Encrypted message, a sequence of upper case letters only
	 */
	public String encrypt(String message) {	
		// COMPLETE THIS METHOD
	    char ch, fM;
        int chNum,key,total;
        String finalMessage = "";
        String encMess = "";
        for(int i = 0;i<message.length();i++){
            if(Character.isLetter(message.charAt(i))){
                ch = Character.toUpperCase(message.charAt(i));
                finalMessage += ch;
            }
        }
        System.out.println(finalMessage);
        for(int x = 0;x <finalMessage.length();x++){
            fM = finalMessage.charAt(x);
            chNum = fM-'A'+1;
            key = getKey();
            total = chNum + key;
            if (total >26){
                total = total -26;
            }
            fM = (char)(total-1+'A');
            encMess += fM;
        }
        printList(deckRear);
        return encMess;
	}
	
	/**
	 * Decrypts a message, which consists of upper case letters only
	 * 
	 * @param message Message to be decrypted
	 * @return Decrypted message, a sequence of upper case letters only
	 */
	public String decrypt(String message) {	
		// COMPLETE THIS METHOD

        String decrypted = "";
        for(int i = 0; i < message.length();i++){
            int numVal = message.charAt(i)-'A'+1;
            int key = getKey();

            if (numVal-key <1){
                int fin = (numVal+26)-key;
                char ch = (char)(fin-1+'A');
                decrypted +=ch;

            }
        }
        System.out.println(decrypted);
	    return decrypted;
	}
    public static void main(String[] args)
            throws IOException {

        Solitaire ss = new Solitaire();

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.print("Enter deck file name => ");

        Scanner sc = new Scanner(new File(br.readLine()));
        ss.makeDeck(sc);

        System.out.print("Encrypt or decrypt? (e/d), press return to quit => ");
        String inp = br.readLine();
        if (inp.length() == 0) {
            System.exit(0);
        }
        char ed = inp.charAt(0);
        System.out.print("Enter message => ");
        String message = br.readLine();
        if (ed == 'e') {
            System.out.println("Encrypted message: " + ss.encrypt(message));
        } else {
            System.out.println("Decrypted message: " + ss.decrypt(message));
        }
    }
}
