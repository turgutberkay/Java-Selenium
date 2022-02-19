Feature: HomePage Feature

  Scenario Outline: : Entering the order summary page with <paymentMethod> and <bank>
    Given Go to "https://www.hepsiburada.com/"
    When Login with "denemepoc123@gmail.com" email and "Denemepoc1" password
    When Search "Kitap" and add basket 3. product
    When Go to order summary page with <bank> and <paymentMethod>
    Then The bank name in the payment information field is checked
    Then Clear basket for next scenarios
    Examples:
      | bank          | paymentMethod |
      | Akbank        | Anında Havale |
      | İş Bankası    | Anında Havale |
      | Vakıfbank     | Anında Havale |
      | Kuveyt Türk   | Anında Havale |
      | AlBaraka Türk | Anında Havale |





