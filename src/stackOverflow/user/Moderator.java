package stackOverflow.user;

import stackOverflow.post.InteractablePost;

public class Moderator extends Member {

    public Moderator(String name) {
        super(name);
    }

    public void deletePost(InteractablePost post) {
        // Deleting logic
        System.out.println("Deleting Post: "+ post);
    }

    public void undeleteQuestion(InteractablePost post) {
        // Undeleting logic
        System.out.println("Undeleting Post: "+ post);
    }
}
