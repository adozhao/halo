package run.halo.app.content;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import java.util.Set;
import org.springframework.lang.Nullable;
import org.springframework.util.MultiValueMap;
import run.halo.app.core.extension.Post;
import run.halo.app.extension.router.IListRequest;

/**
 * A query object for {@link Post} list.
 *
 * @author guqing
 * @since 2.0.0
 */
public class PostQuery extends IListRequest.QueryListRequest {

    public PostQuery(MultiValueMap<String, String> queryParams) {
        super(queryParams);
    }

    @Nullable
    @Schema(name = "contributor")
    public Set<String> getContributors() {
        return listToSet(queryParams.get("contributor"));
    }

    @Nullable
    @Schema(name = "category")
    public Set<String> getCategories() {
        return listToSet(queryParams.get("category"));
    }

    @Nullable
    @Schema(name = "tag")
    public Set<String> getTags() {
        return listToSet(queryParams.get("tag"));
    }

    @Nullable
    Set<String> listToSet(List<String> param) {
        return param == null ? null : Set.copyOf(param);
    }
}