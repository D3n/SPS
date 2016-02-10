package com.supprojectstarter.rest;

import com.supprojectstarter.bean.Contribution;
import com.supprojectstarter.dao.DAOContribution;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import java.net.URI;

@Path("/contributions")
public class ContributionResource {
    @POST
    public Response addContribution(Contribution contribution) {
        DAOContribution.getInstance().insert(contribution);
        URI uri = URI.create("/" + contribution.getId());
        return Response.created(uri).build();
    }
}
