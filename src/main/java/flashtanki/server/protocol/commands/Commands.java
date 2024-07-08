package flashtanki.server.protocol.commands;

import flashtanki.server.ServerProperties;

public enum Commands {
	GetAesData("system", "get_aes_data", "server"),
	SetAesData("system", "set_aes_data", "client"),
	InitLocale("system", "init_locale", "client"),
	InitRegistrationModel("system", "init_registration_model", "client"),
	LoadResources("system", "load_resources", "client"),
	MainResourcesLoaded("system", "main_resources_loaded", "client"),
	DependenciesLoaded("system", "dependencies_loaded", "server"),
	InitInviteModel("system", "initInviteModel", "client"),
	Login("auth", "login", "server"),
	AuthAccept("auth", "accept", "client"),
	AuthDenied("auth", "denied", "client"),
	Error("lobby", "error", "server"),
	ShowServerStop("lobby", "server_halt", "client"),
	InitPremium("lobby", "init_premium", "client"),
	InitPanel("lobby", "init_panel", "client"),
	SetCrystals("lobby", "add_crystall", "client"),
	SetScore("lobby", "add_score", "client"),
	SetRank("lobby", "update_rang", "client"),
	InitBattleSelect("lobby", "init_battle_select", "client"),
	InitBattleCreate("lobby", "init_battle_create", "client"),
	StartLayoutSwitch("lobby", "change_layout_state", "client"),
	EndLayoutSwitch("lobby", "end_layout_switch", "client"),
	ShowSettings("lobby", "showSettings", "server"),
	ClientShowSettings("lobby", "showSettings", "client"),
	CheckPasswordIsSet("lobby", "checkPasswordIsSet", "server"),
	CheckPasswordCorrectness("lobby", "checkPasswordCorrectness", "server"),
	PasswordIsSet("lobby", "notifyPasswordIsSet", "client"),
	PasswordIsNotSet("lobby", "notifyPasswordIsNotSet", "client"),
	AddBattle("lobby", "add_battle", "client"),
	CreateBattle("battle_create", "battle_create", "server"),
	CheckBattleName("battle_create", "checkBattleNameForForbiddenWords", "server"),
	SetCreateBattleName("lobby", "setFilteredBattleName", "client"),
	SelectBattle("battle_select", "select", "server"),
	ClientSelectBattle("battle_select", "select", "client"),
	Fight("battle_select", "fight", "server"),
	SubscribeUserUpdate("lobby", "subscribe_user_update", "server"),
	UnsubscribeUserUpdate("lobby", "unsubscribe_user_update", "server"),
	NotifyPlayerJoinBattle("lobby", "notify_user_battle", "client"),
	NotifyPlayerLeaveBattle("lobby", "notify_user_leave_battle", "client"),
	NotifyUserOnline("lobby", "notify_user_online", "client"),
	NotifyUserRank("lobby", "notify_user_rank", "client"),
	NotifyUserPremium("lobby", "notify_user_premium", "client"),
	NotifyUserUsername("lobby", "notify_user_change_nickname", "client"),
	UnloadBattleSelect("lobby", "unload_battle_select", "client"),
	ShowBattleInfo("lobby", "show_battle_info", "client"),
	StartBattle("lobby", "start_battle", "client"),
	InitBonusesData("battle", "init_bonuses_data", "client"),
	InitBonuses("battle", "init_bonuses", "client"),
	InitShotsData("battle", "init_shots_data", "client"),
	InitBattleModel("battle", "init_battle_model", "client"),
	InitSuicideModel("battle", "init_suicide_model", "client"),
	InitDmModel("battle", "init_dm_model", "client"),
	InitTdmModel("battle", "init_tdm_model", "client"),
	InitCtfModel("battle", "init_ctf_model", "client"),
	InitDomModel("battle", "init_dom_model", "client"),
	InitGuiModel("battle", "init_gui_model", "client"),
	InitMineModel("battle", "init_mine_model", "client"),
	InitDmStatistics("battle", "init_dm_statistics", "client"),
	Ping("battle", "ping", "server"),
	Pong("battle", "pong", "server"),
	InitEffects("battle", "init_effects", "client"),
	InitFlags("battle", "init_flags", "client"),
	ChangeFund("battle", "change_fund", "client"),
	ChangeTeamScore("battle", "change_team_scores", "client"),
	InitTank("battle", "init_tank", "client"),
	GetInitDataLocalTank("battle", "get_init_data_local_tank", "server"),
	ReadyToRespawn("battle", "readyToSpawn", "server"),
	ReadyToSpawn("battle", "readyToPlace", "server"),
	PrepareToSpawn("battle", "prepare_to_spawn", "client"),
	ChangeHealth("battle", "change_health", "client"),
	SpawnTank("battle", "spawn", "client"),
	ActivateTank("battle", "activate_tank", "client"),
	ChangeTankSpecification("battle", "change_spec_tank", "client"),
	InitInventory("battle", "init_inventory", "client"),
    SetItemCount("battle", "updateCount", "client"),
	ActivateItem("battle", "activate_item", "server"),
	ClientActivateItem("battle", "activate_item", "client");
	
    public final String category;
	public final String command;
	public final String side;

	Commands(String categor, String comman, String sid) {
		category = categor;
		command = comman;
		side = sid;
    }
	
	public String concat() {
		return (category + ServerProperties.DELIM_ARGUMENTS_SYMBOL + command);
	}
}
