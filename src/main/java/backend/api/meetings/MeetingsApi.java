package backend.api.meetings;

import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;

public interface MeetingsApi {
    @Operation(summary = "Returns all meetings information.", description = "Returns all meetings information in a list.", tags={ "Meeting" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Request completed.", content = @Content(schema = @Schema(implementation = MeetingPaginatedResponse.class))) })
    @RequestMapping(value = "/meetings",
            produces = { "application/json" },
            method = RequestMethod.GET)
    ResponseEntity<MeetingPaginatedResponse> getMeetings(@ApiParam(value = "the number of the page") @Valid @RequestParam(value = "page", required = false, defaultValue = "0") Integer pageNumber,
                                                       @ApiParam(value = "the number of element per page") @Valid @RequestParam(value = "size", required = false, defaultValue = "25") Integer size);

}
