package Lab5_DesignByInduction;
import java.util.*;


/*
Implement the solution for the algorithm: 
-The successful party problem [Manber, chapter 5.3]
*/

public class PartyPerson {

    private String name;
    private Set<PartyPerson> friends;

    public PartyPerson(String name) {
        this.name = name;
        this.friends = new HashSet<>();
    }

    public String getName() {
        return name;
    }

    public void addFriend(PartyPerson friend) {
        friends.add(friend);
        friend.friends.add(this);
    }

    public Set<PartyPerson> getFriends() {
        return friends;
    }

    public static Set<PartyPerson> Party(Set<PartyPerson> ps, int k) {
        
        int n = ps.size();
        System.out.println("k=" + k);

        while (n > k) {
            boolean allHaveKFriends = true;
            // Check if everyone has at least k friends in ps
            for (PartyPerson p : ps) {
                int numFriendsInPs = 0;
                for (PartyPerson friend : p.getFriends()) {
                    if (ps.contains(friend)) {
                        numFriendsInPs++;
                    }
                }

                if (numFriendsInPs < k) {
                    // Remove p from ps
                    ps.remove(p);
                    n--;
                    allHaveKFriends = false;
                    break;
                }
            }

            if (allHaveKFriends) {
                // All SuccessfulPartys in ps have at least k friends in ps, so invite everyone
                return ps;
            }
        }

        // No one can be invited
        return Collections.emptySet();
    }

    public static void main(String[] args) {
        // Create SuccessfulPartys
        PartyPerson Anna = new PartyPerson("Anna");
        PartyPerson Alex = new PartyPerson("Alex");
        PartyPerson Lucas = new PartyPerson("Lucas");
        PartyPerson Andrew = new PartyPerson("Andrew");

        Anna.addFriend(Alex);
        Anna.addFriend(Lucas); 
        Anna.addFriend(Andrew);
        Alex.addFriend(Anna);
        Alex.addFriend(Andrew);
        Lucas.addFriend(Anna);
        Lucas.addFriend(Andrew);
        Andrew.addFriend(Alex);
        Andrew.addFriend(Lucas);
    
        Set<PartyPerson> invitedGuests = Party(new HashSet<>(Arrays.asList(Anna, Alex, Lucas, Andrew)), 2);

        // Print invited guests
        if (invitedGuests.isEmpty()) {
            System.out.println("No one can be invited to the party.");
        } else {
            System.out.println("Invited guests:");
            for (PartyPerson p : invitedGuests) {
                System.out.println(p.getName());
            }
        }
    }
}
