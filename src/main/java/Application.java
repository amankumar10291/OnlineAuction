import com.auction.resource.AuctionManager;

import java.util.Scanner;

public class Application {

    public static void main(String[] args) {
        AuctionManager auctionManager = new AuctionManager();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("************************************");
            System.out.println("1.Add Buyer\n2.Add Seller\n3.Create Auction\n4.Create/Update Bid\n5.Withdraw Bid\n6.Close Auction\n7.Get Profit/Loss\n8.Exit");
            System.out.println("Provide your input:");
            System.out.println("************************************");
            Integer input = scanner.nextInt();
            switch (input) {
                case 1:
                    System.out.println("Enter Buyer name: ");
                    String buyer = scanner.next();
                    System.out.println(auctionManager.addBuyer(buyer));
                    break;
                case 2:
                    System.out.println("Enter Seller name: ");
                    String seller = scanner.next();
                    System.out.println(auctionManager.addSeller(seller));
                    break;
                case 3:
                    System.out.println("Enter Auction Details(syntax: id,ll,hl,pc,seller) ");
                    String auction = scanner.next();
                    System.out.println(auctionManager.createAuction(auction));
                    break;
                case 4:
                    System.out.println("Enter Bid Details(syntax: buyer,auction,amount) ");
                    String bid = scanner.next();
                    System.out.println(auctionManager.createUpdateBid(bid));
                    break;
                case 5:
                    System.out.println("Enter Bid Details(syntax: buyer,auction) ");
                    String withdraw = scanner.next();
                    System.out.println(auctionManager.withdrawBid(withdraw));
                    break;
                case 6:
                    System.out.println("Enter Auction Details(syntax: id) ");
                    String close = scanner.next();
                    System.out.println(auctionManager.closeAuction(close));
                    break;
                case 7:
                    System.out.println("Enter Profit Loss(syntax: seller, auction) ");
                    String pl = scanner.next();
                    System.out.println(auctionManager.getProfitLoss(pl));
                    break;
                case 8:
                    System.exit(1);
                    break;
                default:
                    System.out.println("Provide correct input!!!!");

            }
        }
    }
}
