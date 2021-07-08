import { supabase } from "./supabase";


async function getUser() {
    const user = {
        "username": "",
    };
    try {
        const supabaseUser = supabase.auth.user();

        const { data, error, status } = await supabase
            .from('user')
            .select(`username`)
            .eq('username', supabaseUser.username)
            .single();

        if (error && status !== 406) throw error;

        if (data) {
            user.username = data.username;
        }
    } catch (error) {
        alert(error.message);
    }
    return user;
}