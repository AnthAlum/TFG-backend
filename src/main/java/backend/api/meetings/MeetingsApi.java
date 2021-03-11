package backend.api.meetings;

import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

public interface MeetingsApi {

    @CrossOrigin(origins = "http://localhost:4200")
    @Operation(summary = "Returns all meetings information.", description = "Returns all meetings information in a list.", tags={ "Meeting" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Request completed.", content = @Content(schema = @Schema(implementation = MeetingPaginatedResponse.class))) })
    @RequestMapping(value = "/meetings",
            produces = { "application/json" },
            method = RequestMethod.GET)
    ResponseEntity<MeetingPaginatedResponse> getMeetings(@ApiParam(value = "the number of the page") @Valid @RequestParam(value = "page", required = false, defaultValue = "0") Integer pageNumber,
                                                       @ApiParam(value = "the number of element per page") @Valid @RequestParam(value = "size", required = false, defaultValue = "25") Integer size);

    @Operation(summary = "Returns all meetings information.", description = "Returns all meetings information in a list.", tags={ "Meeting" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Request completed.", content = @Content(schema = @Schema(implementation = Void.class))) })
    @RequestMapping(value = "/meetings",
            consumes = { "application/json" },
            method = RequestMethod.POST)
    ResponseEntity<Void> registerMeeting(@ApiParam(value = "the new meeting") @Valid @RequestBody MeetingRegistrationRequest meetingRegistrationRequest);

    @Operation(summary = "Deletes a meeting", description = "Deletes a meeting of the given ID.", tags={ "Meeting" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Meeting deleted sucessfully."),

            @ApiResponse(responseCode = "404", description = "Meeting not found with the given ID.") })
    @RequestMapping(value = "/meetings/{meetingId}",
            method = RequestMethod.DELETE)
    ResponseEntity<Void> deleteMeeting(@Parameter(in = ParameterIn.PATH, description = "Meeting's ID", required=true, schema=@Schema()) @PathVariable("meetingId") Long meetingId);

}
