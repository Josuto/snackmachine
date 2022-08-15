package org.elkartech.snackmachine.adapters;

import java.math.BigDecimal;
import java.util.Scanner;

import org.elkartech.snackmachine.application.ApplicationServices;
import org.elkartech.snackmachine.domain.Money;
import org.elkartech.snackmachine.domain.SnackMachine;


/**
 * Application API with the user CLI. To launch it in Eclipse just run it as a
 * Java application. This application modifies the state of a
 * {@link SnackMachine} instance while its running and the user does not perform
 * any illegal operation e.g., order the purchase of a snack without first
 * inserting the an amount of money that covers its price.
 * <p>
 * This application is roughly a Java version of the snack machine application
 * developed by Vladimir Khorikov as part of his course DDD in Practice
 * Pluralsight. The later implements the MVVM pattern that enables two-way data
 * binding between the view and the model. Since I wanted to focus on the DDD
 * tactical design patterns common to all OO languages and frameworks, I decided
 * to trade user data presentation completeness for design simplicity. Thus,
 * although the system is always left in a valid state after any operation
 * execution, users do not get much feedback for the operations they execute.
 * <p>
 * There are three main operations that the user can perform:
 * <ul>
 * <li>insert</li> to insert coins or bills to the snack machine. Once the user
 * has given this order, the system requests her to specify the quantity of the
 * money to insert, which must match that of one of the coins or bills specified
 * at {@link Money#ALL_MONEY_TYPES}.
 * <li>return</li> to return the change back to the user.
 * <li>buy</li> to purchase a snack. Once the user has given this order, the
 * system requests her to specify the slot position of the snack to buy.
 * </ul>
 * <p>
 * <b>Implementation Notes:</b>
 * <ul>
 * <li>The current solution uses exceptions both to complain about programming
 * errors due to e.g., invariant violations and to notify the user about illegal
 * operation invocations or provision of incorrect input parameter values.
 * <b>This is not a good use of exceptions in an application</b>, but keeps the
 * code base fairly simple and allowed me to focus only on the DDD tactical
 * design patters, as exposed earlier.</li>
 * <li>The code base for this application is not fully validated e.g., some unit
 * tests for the {@link ApplicationServices} and {@link Money} classes are
 * missing.</li>
 * </ul>
 * 
 * 
 * @author josumartinez
 *
 */
public class Api {

    public static void main(String[] args) {
        ApplicationServices services = new ApplicationServices(new SnackMachineInMemoryRepositoryImpl());
        
        try (Scanner input = new Scanner(System.in);) {
            while(true) {
            System.out.println("What do you wanna do? ");
                String option = input.next();
                if (option.equals("insert")) {
                    System.out.println("Insert coin: ");
                    BigDecimal amount = input.nextBigDecimal();
                    Money money = Money.of(amount);
                    services.insertMoney(money);
                    System.out.println("Money inserted");
                }
                else if (option.equals("return")) {
                    services.returnChange();
                    System.out.println("Change returned");
                }
                else if (option.equals("buy")) {
                    System.out.println("Insert slot: ");
                    int position = input.nextInt();
                    services.buySnack(position);
                    System.out.println("Snack bought");
                }
                else throw new IllegalArgumentException("The given option does not exist");
            }
        }
        catch(Exception exception) {
            System.out.print(exception.getMessage());
        }
    }

}
