import org.eclipse.egit.github.core.Repository;
import org.eclipse.egit.github.core.SearchRepository;

public class LeGITRepository{

    private int id;
    private String name;
    private String description;
    private int watched;
    private int stars;
    private int forks;
    private String cloneGitURL;

    public LeGITRepository(int id, Repository repository) {
        this.id = id;
        this.name = repository.getName();
        this.description = repository.getDescription();
        this.watched = repository.getOpenIssues();
        this.stars = repository.getWatchers();
        this.forks = repository.getForks();
        this.cloneGitURL = repository.getCloneUrl();
    }

    public LeGITRepository(int id, SearchRepository searchRepository) {
        this.id = id;
        this.name = searchRepository.getName();
        this.description = searchRepository.getDescription();
        this.watched = searchRepository.getOpenIssues();
        this.stars = searchRepository.getWatchers();
        this.forks = searchRepository.getForks();
        this.cloneGitURL = searchRepository.getUrl()+".git";

    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getWatched() {
        return watched;
    }

    public int getStars() {
        return stars;
    }

    public int getForks() {
        return forks;
    }

    public String getCloneGitURL() {
        return cloneGitURL;
    }


}
