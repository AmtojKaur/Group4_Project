package edu.uw.tcss450.group4.weatherchatapp.ui.connections.utils;

import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.IntPredicate;
import java.util.function.Predicate;

/**
 * Interface used to validate user password
 *
 * @author Amtoj Kaur
 * @version 3 May 2023
 */
public interface PasswordValidator
        extends Function<String, Optional<PasswordValidator.ValidationResult>> {

    /**
     * Returns a validator that when applied will validate the length of the String as greater
     * than 6.
     *
     * When a String s is applied to the returning validator, it will evaluate to an Optional
     * containing ValidationResult.SUCCESS when s.length() > 6, otherwise
     * ValidationResult.PWD_INVALID_LENGTH.
     *
     * @return a validator that validates the length of the String as > 6
     */
    static PasswordValidator checkPwdLength() {
        return checkPwdLength(6);
    }

    /**
     * Returns a validator that when applied will validate the length of the String as greater
     * than 6.
     *
     * When a String s is applied to the returning validator, it will evaluate to an Optional
     * containing ValidationResult.SUCCESS when s.length() > length, otherwise
     * ValidationResult.PWD_INVALID_LENGTH.
     *
     * @param length the length of the String needed for validation
     * @return a validator that validates the length of the String as > 6
     */
    static PasswordValidator checkPwdLength(int length) {
        return password ->
                Optional.of(password.length() > length ?
                        ValidationResult.SUCCESS : ValidationResult.PWD_INVALID_LENGTH);
    }

    /**
     * Returns a validator that when applied will validate that the String contains at least
     * one digit.
     *
     * When a String s is applied to the returning validator, it will evaluate to an Optional
     * containing ValidationResult.SUCCESS when s contains at least one digit, otherwise
     * ValidationResult.PWD_MISSING_DIGIT.
     *
     * @return a validator that validates that the String contains a digit
     */
    static PasswordValidator checkPwdDigit() {
        return password ->
                Optional.of(checkStringContains(password, Character::isDigit) ?
                        ValidationResult.SUCCESS : ValidationResult.PWD_MISSING_DIGIT);
    }

    /**
     * Returns a validator that when applied will validate that the String contains at least
     * one uppercase letter.
     *
     * When a String s is applied to the returning validator, it will evaluate to an Optional
     * containing ValidationResult.SUCCESS when s contains at least one uppercase letter,
     * otherwise ValidationResult.PWD_MISSING_UPPER.
     *
     * @return a validator that validates that the String contains an uppercase letter
     */
    static PasswordValidator checkPwdUpperCase() {
        return password ->
                Optional.of(checkStringContains(password, Character::isUpperCase) ?
                        ValidationResult.SUCCESS : ValidationResult.PWD_MISSING_UPPER);
    }

    /**
     * Returns a validator that when applied will validate that the String contains at least
     * one lowercase letter.
     *
     * When a String s is applied to the returning validator, it will evaluate to an Optional
     * containing ValidationResult.SUCCESS when s contains at least one lowercase letter,
     * otherwise ValidationResult.PWD_MISSING_LOWER.
     *
     * @return a validator that validates that the String contains an lowercase letter
     */
    static PasswordValidator checkPwdLowerCase() {
        return password ->
                Optional.of(checkStringContains(password, Character::isLowerCase) ?
                        ValidationResult.SUCCESS : ValidationResult.PWD_MISSING_LOWER);
    }

    /**
     * Returns a validator that when applied will validate that the String contains at least
     * one of these special characters: "@#$%&*!?".
     *
     * When a String s is applied to the returning validator, it will evaluate to an Optional
     * containing ValidationResult.SUCCESS when s contains at least one of these special
     * characters: "@#$%&*!?", otherwise ValidationResult.PWD_MISSING_SPECIAL.
     *
     * @return a validator that validates that the String contains a special character
     */
    static PasswordValidator checkPwdSpecialChar() {
        return checkPwdSpecialChar("@#$%&*!?");
    }

    /**
     * Returns a validator that when applied will validate that the String contains at least
     * one of the characters found in specialChars.
     *
     * When a String s is applied to the returning validator, it will evaluate to an Optional
     * containing ValidationResult.SUCCESS when s contains at least one of the
     * characters found in specialChars, otherwise ValidationResult.PWD_MISSING_SPECIAL.
     *
     * @param specialChars the characters to look for
     * @return a validator that validates that the String contains a special character
     */
    static PasswordValidator checkPwdSpecialChar(String specialChars) {
        return password ->
                Optional.of(checkStringContains(password,
                        c -> specialChars.contains(Character.toString((char) c))) ?
                        ValidationResult.SUCCESS : ValidationResult.PWD_MISSING_SPECIAL);
    }

    /**
     * Returns a validator that when applied will validate that the String does NOT contain ANY
     * of the characters found in excludeChars.
     *
     * When a String s is applied to the returning validator, it will evaluate to an Optional
     * containing ValidationResult.SUCCESS when s does NOT contain ANY of the
     * characters found in exludeChars, otherwise ValidationResult.PWD_INCLUDES_EXCLUDED.
     *
     * @param excludeChars the characters to exclude
     * @return a validator that validates that the String does NOT contain ANY of the characters
     * found in excludeChars
     */
    static PasswordValidator checkPwdDoNotInclude(String excludeChars) {
        return password ->
                Optional.of(!checkStringContains(password, //NOTE the !
                        c -> excludeChars.contains(Character.toString((char) c))) ?
                        ValidationResult.SUCCESS : ValidationResult.PWD_INCLUDES_EXCLUDED);
    }

    /**
     * Returns a validator that when applied will validate that the String does NOT contain ANY
     * whitespace as defined by Character.isWhiteSpace().
     *
     * When a String s is applied to the returning validator, it will evaluate to an Optional
     * containing ValidationResult.SUCCESS when s does NOT contain ANY whitespace, otherwise
     * ValidationResult.PWD_INCLUDES_WHITESPACE.
     *
     * @return a validator that validates that the String does NOT contain ANY whitespace
     */
    static PasswordValidator checkExcludeWhiteSpace() {
        return password ->
                Optional.of(!checkStringContains(password, //NOTE the !
                        Character::isWhitespace) ?
                        ValidationResult.SUCCESS : ValidationResult.PWD_INCLUDES_WHITESPACE);
    }

    /**
     * Returns a validator that when applied will validate a String based on theTest Predicate.
     *
     * When a String s is applied to the returning validator, it will evaluate to an Optional
     * containing ValidationResult.SUCCESS when s passes theTest predicate, otherwise
     * ValidationResult.PWD_CLIENT_ERROR.
     *
     * @param theTest a predicate to test the String for validation
     * @return a validator that validates that the String based on theTest Predicate
     */
    static PasswordValidator checkClientPredicate(Predicate<String> theTest) {
        return password ->
                Optional.of(theTest.test(password) ?
                        ValidationResult.SUCCESS : ValidationResult.PWD_CLIENT_ERROR);
    }

    /**
     * Helper, determines if the String check contains at least one character that test
     * evaluates true for.
     *
     * @param check The String to test
     * @param test the character test
     * @return true if check contains at least one character that test evaluates true for,
     * false otherwise
     */
    static boolean checkStringContains(String check, IntPredicate test) {
        return check.chars().filter(test).count() > 0;
    }

    /**
     * Returns a composed PasswordValidator that represents a short-circuiting logical AND of
     * this PasswordValidator and another.  When evaluating the composed PasswordValidator,
     * if this PasswordValidator in not successful, then the other PasswordValidator is not
     * evaluated.
     *
     * NOTE: THIS is the Combinator!
     *
     * @param other a PasswordValidator that will be logically-ANDed with this
     *      PasswordValidator
     * @return a composed PasswordValidator that represents a short-circuiting logical AND of
     *      this PasswordValidator and another
     */
    default PasswordValidator and(PasswordValidator other) {
        return password -> this.apply(password)
                .flatMap(result -> result == ValidationResult.SUCCESS ?
                        other.apply(password) : Optional.of(result));

        /*
         * NOTE: If you have access to Java 1.9 (Which AS still doesn't support) you can simplify
         * the above return statement to below. The or method was introduced to the Optional
         * class in Java 1.9.
         */
//        return password -> this.apply(password)
//                .filter(result -> result != SUCCESS)
//                .or(() -> other.apply(password));
    }

    /**
     * Returns a composed PasswordValidator that represents a short-circuiting logical OR of
     * this PasswordValidator and another.  When evaluating the composed PasswordValidator,
     * if this PasswordValidator is successful, then the other PasswordValidator is not
     * evaluated.
     *
     * NOTE: THIS is the Combinator!
     *
     * @param other a PasswordValidator that will be logically-ORed with this
     *      PasswordValidator
     * @return a composed PasswordValidator that represents a short-circuiting logical OR of
     *      this PasswordValidator and another
     */
    default PasswordValidator or(PasswordValidator other) {
        return password -> this.apply(password)
                .flatMap(result -> result == ValidationResult.SUCCESS ?
                        Optional.of(result): other.apply(password));
    }

    /**
     * This helper method is a work around used since Android does not support java language
     * features introduced after Java 1.8. The Optional class introduced several helpful methods
     * in Java 1.9 that should be used here instead of this.
     * @param result the result of a validation action
     * @param onSuccess the action to take when the password successfully validates
     * @param onError the action to take when the password unsuccessfully validates
     */
    default void processResult(Optional<ValidationResult> result,
                               Runnable onSuccess,
                               Consumer<ValidationResult> onError) {
        if (result.isPresent()) {
            if (result.get() == ValidationResult.SUCCESS) {
                onSuccess.run();
            } else {
                onError.accept(result.get());
            }
        } else {
            throw new IllegalStateException("Nothing to process");
        }
    }

    /**
     *
     * @author Charles Bryan
     * @version Spring 2020
     */
    enum ValidationResult {
        SUCCESS,
        PWD_INVALID_LENGTH,
        PWD_MISSING_DIGIT,
        PWD_MISSING_UPPER,
        PWD_MISSING_LOWER,
        PWD_MISSING_SPECIAL,
        PWD_INCLUDES_EXCLUDED,
        PWD_INCLUDES_WHITESPACE,
        PWD_CLIENT_ERROR
    }
}