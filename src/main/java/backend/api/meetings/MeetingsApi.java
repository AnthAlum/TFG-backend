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
            @ApiResponse(responseCode = "200", description = "Meeting deleted successfully."),

            @ApiResponse(responseCode = "404", description = "Meeting not found with the given ID.") })
    @RequestMapping(value = "/meetings/{meetingId}",
            method = RequestMethod.DELETE)
    ResponseEntity<Void> deleteMeeting(@Parameter(in = ParameterIn.PATH, description = "Meeting's ID", required=true, schema=@Schema()) @PathVariable("meetingId") Long meetingId);

    @Operation(summary = "Modify meeting's date", description = "Modify meeting's date", tags = { "Meeting" })
    @io.swagger.v3.oas.annotations.responses.ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Meeting's date modified successfully"),
            @ApiResponse(responseCode = "404", description = "Meeting not found with the given ID")
    })
    @RequestMapping(value = "/meetings/{meetingId}/date",
            consumes = { "application/json" },
            method = RequestMethod.PUT)
    ResponseEntity<Void> modifyDate(@Parameter(in = ParameterIn.PATH, description = "Meeting's Id", required = true, schema = @Schema()) @PathVariable("meetingId") Long meetingId, @ApiParam(value = "the new date") @Valid @RequestBody MeetingDateChangeRequest meetingDateChangeRequest);

    @Operation(summary = "Modify meeting's matter", description = "Modify meeting's matter", tags = { "Meeting" })
    @io.swagger.v3.oas.annotations.responses.ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Meeting's date modified successfully"),
            @ApiResponse(responseCode = "404", description = "Meeting not found with the given ID")
    })
    @RequestMapping(value = "/meetings/{meetingId}/matter",
            consumes = { "application/json" },
            method = RequestMethod.PUT)
    ResponseEntity<Void> modifyMatter(@Parameter(in = ParameterIn.PATH, description = "Meeting's Id", required = true, schema = @Schema()) @PathVariable("meetingId") Long meetingId, @ApiParam(value = "the new date") @Valid @RequestBody MeetingMatterChangeRequest meetingMatterChangeRequest);

    @Operation(summary = "Modify one meeting's merchant", description = "Modify one meeting's merchant", tags = { "Meeting" })
    @io.swagger.v3.oas.annotations.responses.ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Meeting's merchant modified successfully"),
            @ApiResponse(responseCode = "404", description = "Meeting not found with the given ID")
    })
    @RequestMapping(value = "/meetings/{meetingId}/merchant",
            consumes = { "application/json" },
            method = RequestMethod.PUT)
    ResponseEntity<Void> modifyMerchant(@Parameter(in = ParameterIn.PATH, description = "Meeting's Id", required = true, schema = @Schema()) @PathVariable("meetingId") Long meetingId, @ApiParam(value = "merchant and operation information") @Valid @RequestBody MeetingMerchantChangeRequest meetingMerchantChangeRequest);

    @Operation(summary = "Modify one meeting's client", description = "Modify one meeting's client", tags = { "Client" })
    @io.swagger.v3.oas.annotations.responses.ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Meeting's client modified successfully"),
            @ApiResponse(responseCode = "404", description = "Meeting not found with the given ID")
    })
    @RequestMapping(value = "/meetings/{meetingId}/client",
            consumes = { "application/json" },
            method = RequestMethod.PUT)
    ResponseEntity<Void> modifyClient(@Parameter(in = ParameterIn.PATH, description = "Meeting's Id", required = true, schema = @Schema()) @PathVariable("meetingId") Long meetingId, @ApiParam(value = "client and operation information") @Valid @RequestBody MeetingClientChangeRequest meetingClientChangeRequest);

}
